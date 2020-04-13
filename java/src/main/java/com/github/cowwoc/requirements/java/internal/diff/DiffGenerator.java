/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.GlobalRequirements;
import com.github.cowwoc.requirements.java.internal.util.Strings;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.InsertDelta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_PATTERN;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * Generates a diff of two Strings.
 */
public final class DiffGenerator
{
	// See https://www.regular-expressions.info/unicode.html for an explanation of \p{Zs}
	private static final Pattern WORDS = Pattern.compile("\\p{Zs}+|\r?\n|[.\\[\\](){}/\\\\*+\\-#]");
	private static final String QUOTED_NEWLINE_MARKER = Matcher.quoteReplacement(NEWLINE_MARKER);
	private final TerminalEncoding encoding;
	private final String paddingMarker;

	/**
	 * @param encoding the terminal encoding
	 * @throws AssertionError if {@code encoding} is null
	 */
	public DiffGenerator(TerminalEncoding encoding)
	{
		assert (encoding != null) : "encoding may not be null";
		this.encoding = encoding;
		this.paddingMarker = getPaddingMarker();
	}

	/**
	 * @return the padding character used to align values vertically
	 */
	@SuppressWarnings("DuplicateBranchesInSwitch")
	private String getPaddingMarker()
	{
		switch (encoding)
		{
			case NONE:
				return TextOnly.DIFF_PADDING;
			case XTERM_8_COLORS:
				return Writer8Colors.DIFF_PADDING;
			case XTERM_16_COLORS:
				return Writer16Colors.DIFF_PADDING;
			case XTERM_256_COLORS:
				return Writer256Colors.DIFF_PADDING;
			case RGB_888_COLORS:
				return Writer16MillionColors.DIFF_PADDING;
			default:
				throw new AssertionError(encoding.name());
		}
	}

	/**
	 * Generates the diff of two strings.
	 * <p>
	 * <b>NOTE</b>: Colors may be disabled when stdin or stdout are redirected. To override this
	 * behavior, use {@link GlobalRequirements#withTerminalEncoding(TerminalEncoding)}.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return the calculated diff
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult diff(String actual, String expected)
	{
		// Mark the end of the string to guard against cases that end with whitespace
		List<Integer> actualCodepoints = Strings.toCodepoints(actual + EOS_MARKER);
		List<Integer> expectedCodepoints = Strings.toCodepoints(expected + EOS_MARKER);

		try
		{
			DiffWriter writer = createDiffWriter();
			// DiffUtils.diff() returns a list of deltas, where each delta is associated with a list of characters.
			List<AbstractDelta<Integer>> deltas = DiffUtils.diff(actualCodepoints, expectedCodepoints).getDeltas();
			// DiffUtils.diff() does not return equal deltas, so we add them.
			deltas = addEqualDeltas(deltas, actualCodepoints, expectedCodepoints);
			// Convert a list of deltas and their associated words to a list of words and their associated deltas,
			// using "actual" to define word boundaries.
			new ReduceDeltasPerWord(deltas).run();
			for (AbstractDelta<Integer> delta : deltas)
				writeDelta(delta, writer);
			writer.close();
			return new DiffResult(writer.getActualLines(), writer.getDiffLines(), writer.getExpectedLines());
		}
		catch (DiffException e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * For every word associated with 2 or more unequal deltas, replace the deltas with a single
	 * {@code [DELETE actual, INSERT expected]} pair.
	 */
	private static class ReduceDeltasPerWord implements Runnable
	{
		// Format: [optional] (mandatory)
		//
		// word: (start-delta) (end-delta)
		// start-delta: [pre-word] [delimiter] (word-in-start-delta)
		// end-delta: (word-in-end-delta) [delimiter] [post-word]
		// delimiter: whitespace found in EQUAL deltas
		private final List<AbstractDelta<Integer>> deltas;
		private int numberOfDeltas;

		/**
		 * The index of the start delta.
		 */
		private int indexOfStartDelta;
		/**
		 * The index of the word in the start delta.
		 */
		private int indexOfWordInStartDelta;
		/**
		 * The index of the end delta.
		 */
		private int indexOfEndDelta;
		/**
		 * The index of the delimiter in the end delta. If there is no delimiter, points to the end of the string.
		 */
		private int indexOfDelimiterInEndDelta;
		/**
		 * The index of the start of the next word in the end delta. If there are no followup words, points to the
		 * end of the string.
		 */
		private int indexOfNextWordInEndDelta;

		public ReduceDeltasPerWord(List<AbstractDelta<Integer>> deltas)
		{
			this.deltas = deltas;
			numberOfDeltas = deltas.size();
		}

		@Override
		public void run()
		{
			// We are looking for words that span multiple deltas. If the current delta contains multiple
			// words, we are interested in the latest one.
			findFirstWord();
			if (indexOfStartDelta == numberOfDeltas)
				return;
			while (true)
			{
				findEndOfWord();
				updateDeltas();
				if (!findNextWord())
					return;
			}
		}

		private void findFirstWord()
		{
			// Words start after a whitespace delimiter within an EQUAL delta. If none is found, the start
			// of the first delta acts as a word boundary.
			AbstractDelta<Integer> delta = deltas.get(0);
			MatchResult result;
			indexOfStartDelta = 0;
			String actual = Strings.fromCodepoints(delta.getSource().getLines());
			result = Strings.lastIndexOf(actual, WORDS).orElse(null);
			if (result == null)
				indexOfWordInStartDelta = 0;
			else
				indexOfWordInStartDelta = result.end();
		}

		/**
		 * Finds the end of the word. Words end before a whitespace delimiter within an EQUAL delta.
		 */
		private void findEndOfWord()
		{
			// Words end at a whitespace delimiter found within an EQUAL delta. If none is found, the end of the
			// last delta acts as a word boundary.
			for (int i = indexOfStartDelta + 1; i < numberOfDeltas; ++i)
			{
				AbstractDelta<Integer> delta = deltas.get(i);
				if (delta.getType() == DeltaType.EQUAL)
				{
					String actual = Strings.fromCodepoints(delta.getSource().getLines());
					Matcher matcher = WORDS.matcher(actual);
					if (matcher.find())
					{
						indexOfDelimiterInEndDelta = matcher.start();
						indexOfNextWordInEndDelta = matcher.end();
						indexOfEndDelta = i;
						return;
					}
				}
			}
			indexOfEndDelta = numberOfDeltas - 1;
		}

		private void updateDeltas()
		{
			List<AbstractDelta<Integer>> deltasInWord = deltas.subList(indexOfStartDelta, indexOfEndDelta + 1);
			if (numberOfUnequalDeltas(deltasInWord) <= 2)
			{
				// If the word contains 2 or less unequal deltas then provide character-level granularity.
				//
				// Good:
				// -----=====-----
				// -----+++++=====
				// =====-----+++++
				//
				// Bad:
				// =====-----=====-----
				// +++++=====+++++=====
				// -----++++++----=====
				return;
			}
			// Otherwise, replace the deltas with a single [DELETE, INSERT] pair
			StringBuilder actualBuilder = new StringBuilder();
			StringBuilder expectedBuilder = new StringBuilder();
			List<AbstractDelta<Integer>> updatedDeltas = new ArrayList<>();
			processStartDelta(actualBuilder, expectedBuilder, updatedDeltas);
			processMiddleDeltas(actualBuilder, expectedBuilder);
			processEndDelta(actualBuilder, expectedBuilder, updatedDeltas);

			int oldSize = deltasInWord.size();
			deltasInWord.clear();
			deltasInWord.addAll(updatedDeltas);
			int newSize = deltasInWord.size();
			int deltasRemoved = oldSize - newSize;
			numberOfDeltas -= deltasRemoved;
			indexOfEndDelta -= deltasRemoved;
			indexOfNextWordInEndDelta -= indexOfDelimiterInEndDelta;
		}

		/**
		 * Processes the start delta.
		 *
		 * @param actualBuilder   a buffer to insert the actual value of the word into
		 * @param expectedBuilder a buffer to insert the expected value of the word into
		 * @param updatedDeltas   a list to insert updated deltas into
		 */
		private void processStartDelta(StringBuilder actualBuilder, StringBuilder expectedBuilder,
		                               List<AbstractDelta<Integer>> updatedDeltas)
		{
			AbstractDelta<Integer> delta = deltas.get(indexOfStartDelta);
			String actual = Strings.fromCodepoints(delta.getSource().getLines());
			String actualWord = actual.substring(indexOfWordInStartDelta);
			List<Integer> codepointsBeforeActual = Strings.toCodepoints(
				actual.substring(0, indexOfWordInStartDelta));

			String expectedWord;
			List<Integer> codepointsBeforeExpected;
			if (delta.getType() == DeltaType.EQUAL)
			{
				expectedWord = actualWord;
				codepointsBeforeExpected = codepointsBeforeActual;
			}
			else
			{
				expectedWord = Strings.fromCodepoints(delta.getTarget().getLines());
				codepointsBeforeExpected = List.of();
			}
			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			updatedDeltas.add(deltaWithChunks(delta,
				new Chunk<>(delta.getSource().getPosition(), codepointsBeforeActual),
				new Chunk<>(delta.getTarget().getPosition(), codepointsBeforeExpected)));
		}

		/**
		 * Processes the middle deltas.
		 *
		 * @param actualBuilder   a buffer to insert the actual value of the word into
		 * @param expectedBuilder a buffer to insert the expected value of the word into
		 */
		private void processMiddleDeltas(StringBuilder actualBuilder, StringBuilder expectedBuilder)
		{
			for (int i = indexOfStartDelta + 1; i < indexOfEndDelta; ++i)
			{
				AbstractDelta<Integer> delta = deltas.get(i);
				String actual = Strings.fromCodepoints(delta.getSource().getLines());

				String expected;
				if (delta.getType() == DeltaType.EQUAL)
					expected = actual;
				else
					expected = Strings.fromCodepoints(delta.getTarget().getLines());
				actualBuilder.append(actual);
				expectedBuilder.append(expected);
			}
		}

		/**
		 * Processes the end delta.
		 *
		 * @param actualBuilder   a buffer to insert the actual value of the word into
		 * @param expectedBuilder a buffer to insert the expected value of the word into
		 * @param updatedDeltas   a list to insert updated deltas into
		 */
		private void processEndDelta(StringBuilder actualBuilder, StringBuilder expectedBuilder,
		                             List<AbstractDelta<Integer>> updatedDeltas)
		{
			AbstractDelta<Integer> delta = deltas.get(indexOfEndDelta);
			String actual = Strings.fromCodepoints(delta.getSource().getLines());
			String actualWord = actual.substring(0, indexOfDelimiterInEndDelta);

			// Word before delimiter
			String expectedWord;
			if (delta.getType() == DeltaType.EQUAL)
				expectedWord = actualWord;
			else
			{
				String expected = Strings.fromCodepoints(delta.getTarget().getLines());
				expectedWord = expected.substring(indexOfNextWordInEndDelta);
			}

			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			// Word after delimiter
			AbstractDelta<Integer> deltaAfterWord;
			if (indexOfDelimiterInEndDelta < actual.length())
			{
				List<Integer> codepointsAfterActual = Strings.toCodepoints(
					actual.substring(indexOfDelimiterInEndDelta));
				List<Integer> codepointsAfterExpected;
				if (delta.getType() == DeltaType.EQUAL)
					codepointsAfterExpected = codepointsAfterActual;
				else
				{
					String expected = Strings.fromCodepoints(delta.getTarget().getLines());
					codepointsAfterExpected = Strings.toCodepoints(expected.substring(0, indexOfWordInStartDelta));
				}

				deltaAfterWord = deltaWithChunks(delta,
					new Chunk<>(delta.getSource().getPosition(), codepointsAfterActual),
					new Chunk<>(delta.getTarget().getPosition(), codepointsAfterExpected));
			}
			else
				deltaAfterWord = null;

			AbstractDelta<Integer> deleteActual = new DeleteDelta<>(
				new Chunk<>(delta.getSource().getPosition(), Strings.toCodepoints(actualBuilder.toString())),
				new Chunk<>(delta.getTarget().getPosition(), List.of()));
			AbstractDelta<Integer> insertExpected = new InsertDelta<>(
				new Chunk<>(delta.getSource().getPosition(), List.of()),
				new Chunk<>(delta.getTarget().getPosition(),
					Strings.toCodepoints(expectedBuilder.toString())));
			updatedDeltas.add(deleteActual);
			updatedDeltas.add(insertExpected);
			if (deltaAfterWord != null)
				updatedDeltas.add(deltaAfterWord);
		}

		/**
		 * Finds the next word.
		 *
		 * @return false if there are no more words to be found
		 */
		private boolean findNextWord()
		{
			indexOfStartDelta = indexOfEndDelta;
			indexOfWordInStartDelta = indexOfNextWordInEndDelta;
			return indexOfStartDelta != numberOfDeltas - 1;
		}
	}

	/**
	 * Returns a new delta having the same type as {@code delta} with the specified chunks.
	 *
	 * @param delta  the reference delta
	 * @param source the new source chunk
	 * @param target the new target chunk
	 * @return the new delta
	 */
	public static AbstractDelta<Integer> deltaWithChunks(AbstractDelta<Integer> delta,
	                                                     Chunk<Integer> source, Chunk<Integer> target)
	{
		switch (delta.getType())
		{
			case EQUAL:
				return new EqualDelta<>(source, target);
			case DELETE:
				return new DeleteDelta<>(source, target);
			case INSERT:
				return new InsertDelta<>(source, target);
			case CHANGE:
				return new ChangeDelta<>(source, target);
			default:
				throw new IllegalStateException("Unexpected value: " + delta.getType());
		}
	}

	/**
	 * Adds EqualDelta entries (not added by DiffUtils.diff()).
	 *
	 * @param <T>    the type of elements in the lists
	 * @param deltas the list of deltas
	 * @param source the source list
	 * @param target the target list
	 * @return the updated list
	 */
	private <T> List<AbstractDelta<T>> addEqualDeltas(List<AbstractDelta<T>> deltas, List<T> source,
	                                                  List<T> target)
	{
		// WORKAROUND: https://github.com/java-diff-utils/java-diff-utils/issues/42
		List<AbstractDelta<T>> result = new ArrayList<>();
		int sourceStartPosition = 0;
		int targetStartPosition = 0;
		for (AbstractDelta<T> delta : deltas)
		{
			int sourceEndPosition = delta.getSource().getPosition();
			int targetEndPosition = delta.getTarget().getPosition();
			if (sourceStartPosition < sourceEndPosition || targetStartPosition < targetEndPosition)
			{
				// The gaps between deltas denote equal chunks
				result.add(new EqualDelta<>(
					new Chunk<>(sourceStartPosition, source.subList(sourceStartPosition, sourceEndPosition)),
					new Chunk<>(targetStartPosition, target.subList(targetStartPosition, targetEndPosition))));
			}
			sourceStartPosition = delta.getSource().last() + 1;
			targetStartPosition = delta.getTarget().last() + 1;
			result.add(delta);
		}
		// Add final chunk, if necessary.
		if (sourceStartPosition < source.size() || targetStartPosition < target.size())
		{
			// Deltas do not contain equal chunks. We need to derive those ourselves.
			result.add(new EqualDelta<>(
				new Chunk<>(sourceStartPosition, source.subList(sourceStartPosition, source.size())),
				new Chunk<>(targetStartPosition, target.subList(targetStartPosition, target.size()))));
		}
		return result;
	}

	/**
	 * Write a single delta.
	 *
	 * @param delta  a delta
	 * @param writer the writer to write into
	 */
	private void writeDelta(AbstractDelta<Integer> delta, DiffWriter writer)
	{
		switch (delta.getType())
		{
			case EQUAL:
			{
				String text = Strings.fromCodepoints(delta.getSource().getLines());
				for (String line : splitLines(text))
					writer.writeEqual(line);
				break;
			}
			case DELETE:
			{
				String text = Strings.fromCodepoints(delta.getSource().getLines());
				for (String line : splitLines(text))
					writer.writeDeleted(line);
				break;
			}
			case INSERT:
			{
				String text = Strings.fromCodepoints(delta.getTarget().getLines());
				for (String line : splitLines(text))
					writer.writeInserted(line);
				break;
			}
			case CHANGE:
			{
				String deleted = Strings.fromCodepoints(delta.getSource().getLines());
				String inserted = Strings.fromCodepoints(delta.getTarget().getLines());
				for (String line : splitLines(deleted))
					writer.writeDeleted(line);
				for (String line : splitLines(inserted))
					writer.writeInserted(line);
				break;
			}
			default:
				throw new AssertionError("Unexpected type: " + delta.getType());
		}
	}

	/**
	 * Splits text into one or more lines, replacing newline characters with a {@code NEWLINE_MARKER}.
	 *
	 * @param text some text
	 * @return a list of lines
	 */
	private static List<String> splitLines(CharSequence text)
	{
		List<String> result = new ArrayList<>();
		StringBuilder line = new StringBuilder();
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0; i < lines.length; ++i)
		{
			line.delete(0, line.length());
			line.append(lines[i]);
			if (i < lines.length - 1)
				line.append(NEWLINE_MARKER);
			result.add(line.toString());
		}
		return result;
	}

	/**
	 * @param <T>    the type of elements being compared
	 * @param deltas a list of deltas
	 * @return the number of deltas whose type is not EQUAL
	 */
	private static <T> int numberOfUnequalDeltas(List<AbstractDelta<T>> deltas)
	{
		int result = 0;
		for (AbstractDelta<T> delta : deltas)
		{
			switch (delta.getType())
			{
				case EQUAL:
					break;
				case DELETE:
				case INSERT:
				{
					++result;
					break;
				}
				case CHANGE:
				{
					// Equivalent to DELETE followed by INSERT
					result += 2;
					break;
				}
				default:
					throw new AssertionError(delta.getType().name());
			}
		}
		return result;
	}

	/**
	 * @return a new writer
	 */
	private DiffWriter createDiffWriter()
	{
		switch (encoding)
		{
			case NONE:
				return new TextOnly();
			case XTERM_8_COLORS:
				return new Writer8Colors();
			case XTERM_16_COLORS:
				return new Writer16Colors();
			case XTERM_256_COLORS:
				return new Writer256Colors();
			case RGB_888_COLORS:
				return new Writer16MillionColors();
			default:
				throw new AssertionError(encoding.name());
		}
	}

	/**
	 * @param line a line
	 * @return true if {@code line} only contains padding characters
	 */
	public boolean isEmpty(String line)
	{
		switch (encoding)
		{
			case NONE:
				break;
			case XTERM_8_COLORS:
			case XTERM_16_COLORS:
			case XTERM_256_COLORS:
			case RGB_888_COLORS:
			{
				line = line.replaceAll(Pattern.quote(PREFIX) + ".+?" + Pattern.quote(POSTFIX), "");
				break;
			}
			default:
				throw new AssertionError(encoding.name());
		}
		return Strings.containsOnly(line, paddingMarker);
	}
}
