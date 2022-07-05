/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.GlobalRequirements;
import com.github.cowwoc.requirements.java.internal.util.Strings;
import com.github.cowwoc.requirements.natives.terminal.TerminalEncoding;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.InsertDelta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOS_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * Generates a diff of two Strings.
 */
public final class DiffGenerator
{
	// See https://www.regular-expressions.info/unicode.html for an explanation of \p{Zs}
	private static final Pattern WORDS = Pattern.compile("\\p{Zs}+|\r?\n|(?:\\\\r)?\\\\n|[.\\[\\]()" +
		"{}/\\\\*+\\-#:;]");
	private final TerminalEncoding encoding;
	private final String paddingMarker;
	private final ReduceDeltasPerWord reduceDeltasPerWord = new ReduceDeltasPerWord();

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

		DiffWriter writer = createDiffWriter();
		// DiffUtils.diff() returns a list of deltas, where each delta is associated with a list of characters.
		List<AbstractDelta<Integer>> deltas = DiffUtils.diff(actualCodepoints, expectedCodepoints, true).
			getDeltas();
		reduceDeltasPerWord.accept(deltas);
		for (AbstractDelta<Integer> delta : deltas)
			writeDelta(delta, writer);
		writer.flush();
		return new DiffResult(writer.getActualLines(), writer.getDiffLines(), writer.getExpectedLines(),
			writer.getEqualLines());
	}

	/**
	 * For every word associated with 2 or more unequal deltas, replace the deltas with a single
	 * {@code [DELETE actual, INSERT expected]} pair.
	 */
	private static class ReduceDeltasPerWord implements Consumer<List<AbstractDelta<Integer>>>
	{
		// Format: [optional] (mandatory)
		//
		// word: (start-delta) (end-delta)
		// start-delta: [pre-word] [delimiter] (word-in-start-delta)
		// end-delta: (word-in-end-delta) [delimiter] [post-word]
		// delimiter: whitespace found in EQUAL deltas
		private List<AbstractDelta<Integer>> deltas;
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

		@Override
		public void accept(List<AbstractDelta<Integer>> deltas)
		{
			this.deltas = deltas;
			numberOfDeltas = deltas.size();
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

		/**
		 * Finds the first word.
		 */
		private void findFirstWord()
		{
			// Words start after a whitespace delimiter within an EQUAL delta. If none is found, the start
			// of the first delta acts as a word boundary.
			AbstractDelta<Integer> delta = deltas.get(0);
			indexOfStartDelta = 0;
			String actual = Strings.fromCodepoints(delta.getSource().getLines());
			MatchResult result = Strings.lastIndexOf(actual, WORDS).orElse(null);
			if (result == null)
				indexOfWordInStartDelta = 0;
			else
				indexOfWordInStartDelta = result.end();
		}

		/**
		 * Finds the end of the word.
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

		/**
		 * Update the deltas if necessary.
		 */
		private void updateDeltas()
		{
			assert (!deltas.isEmpty());
			List<AbstractDelta<Integer>> deltasInWord = deltas.subList(indexOfStartDelta, indexOfEndDelta + 1);
			if (deltasInWord.size() < 2)
				return;
			// Improve readability by avoiding many diffs per word or short diffs in short words
			if (numberOfUnequalDeltas(deltasInWord) <= 2 &&
				(shortestDelta(deltasInWord) >= 3 || longestWord(deltasInWord) >= 5))
			{
				// Provide character-level granularity
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
				//
				// Good:
				// football
				// ----====++++
				//     ballroom
				//
				// Bad:
				// 123
				// -+=
				// 133
				return;
			}
			// Otherwise, replace the deltas with a single [DELETE, INSERT] pair
			StringBuilder actualBuilder = new StringBuilder();
			StringBuilder expectedBuilder = new StringBuilder();
			List<AbstractDelta<Integer>> updatedDeltas = new ArrayList<>();
			processStartDelta(actualBuilder, expectedBuilder, updatedDeltas);
			processMiddleDeltas(actualBuilder, expectedBuilder);
			processEndDelta(actualBuilder, expectedBuilder, updatedDeltas);

			int deltasRemoved = deltasInWord.size() - updatedDeltas.size();
			deltasInWord.clear();
			deltasInWord.addAll(updatedDeltas);
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
			List<Integer> codepointsBeforeActualWord = Strings.toCodepoints(
				actual.substring(0, indexOfWordInStartDelta));

			String expectedWord;
			List<Integer> codepointsBeforeExpectedWord;
			if (delta.getType() == DeltaType.EQUAL)
			{
				expectedWord = actualWord;
				codepointsBeforeExpectedWord = codepointsBeforeActualWord;
			}
			else
			{
				expectedWord = Strings.fromCodepoints(delta.getTarget().getLines());
				codepointsBeforeExpectedWord = List.of();
			}
			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			if (indexOfWordInStartDelta > 0)
			{
				updatedDeltas.add(delta.withChunks(
					new Chunk<>(delta.getSource().getPosition(), codepointsBeforeActualWord),
					new Chunk<>(delta.getTarget().getPosition(), codepointsBeforeExpectedWord)));
			}
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
				expectedWord = expected.substring(0, indexOfDelimiterInEndDelta);
			}
			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			AbstractDelta<Integer> deleteActual = new DeleteDelta<>(
				new Chunk<>(delta.getSource().getPosition(), Strings.toCodepoints(actualBuilder.toString())),
				new Chunk<>(delta.getTarget().getPosition(), List.of()));
			AbstractDelta<Integer> insertExpected = new InsertDelta<>(
				new Chunk<>(delta.getSource().getPosition(), List.of()),
				new Chunk<>(delta.getTarget().getPosition(),
					Strings.toCodepoints(expectedBuilder.toString())));
			updatedDeltas.add(deleteActual);
			updatedDeltas.add(insertExpected);

			// Word after delimiter
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
					codepointsAfterExpected = Strings.toCodepoints(expected.substring(indexOfDelimiterInEndDelta));
				}

				updatedDeltas.add(delta.withChunks(
					new Chunk<>(delta.getSource().getPosition(), codepointsAfterActual),
					new Chunk<>(delta.getTarget().getPosition(), codepointsAfterExpected)));
			}
		}

		/**
		 * Finds the next word.
		 *
		 * @return false if there are no more words to be found
		 */
		private boolean findNextWord()
		{
			indexOfStartDelta = indexOfEndDelta;
			if (indexOfStartDelta == numberOfDeltas - 1)
				return false;

			// Similar logic as findFirstWord()
			AbstractDelta<Integer> delta = deltas.get(indexOfStartDelta);
			if (delta.getType() == DeltaType.EQUAL)
			{
				String actual = Strings.fromCodepoints(delta.getSource().getLines());
				MatchResult result = Strings.lastIndexOf(actual, WORDS).orElseThrow(() ->
					new AssertionError("Expecting result to be equal to " +
						"indexOfNextWordInEndDelta (" + indexOfNextWordInEndDelta + ") or later.\n" +
						"actual: '" + actual + "'"));
				indexOfWordInStartDelta = result.end();
			}
			return true;
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
		 * @param deltas a list of deltas
		 * @return the length of the shortest delta
		 */
		private int shortestDelta(List<AbstractDelta<Integer>> deltas)
		{
			assert (!deltas.isEmpty());
			int result = Integer.MAX_VALUE;
			for (AbstractDelta<Integer> delta : deltas)
			{
				switch (delta.getType())
				{
					case EQUAL:
						break;
					case DELETE:
					{
						result = Math.min(result, Strings.fromCodepoints(delta.getSource().getLines()).length());
						break;
					}
					case INSERT:
					{
						result = Math.min(result, Strings.fromCodepoints(delta.getTarget().getLines()).length());
						break;
					}
					case CHANGE:
					{
						// Equivalent to DELETE followed by INSERT
						result = Math.min(result, Strings.fromCodepoints(delta.getSource().getLines()).length());
						result = Math.min(result, Strings.fromCodepoints(delta.getTarget().getLines()).length());
						break;
					}
					default:
						throw new AssertionError(delta.getType().name());
				}
			}
			return result;
		}

		/**
		 * @param deltas a list of deltas
		 * @return the length of the longest word (source or target) spanned by the deltas
		 */
		private int longestWord(List<AbstractDelta<Integer>> deltas)
		{
			assert (!deltas.isEmpty());
			int lengthOfSource = 0;
			int lengthOfTarget = 0;
			for (AbstractDelta<Integer> delta : deltas)
			{
				switch (delta.getType())
				{
					case EQUAL:
					{
						int length = delta.getSource().size();
						lengthOfSource += length;
						lengthOfTarget += length;
						break;
					}
					case DELETE:
					{
						lengthOfSource += Strings.fromCodepoints(delta.getSource().getLines()).length();
						break;
					}
					case INSERT:
					{
						lengthOfTarget += Strings.fromCodepoints(delta.getTarget().getLines()).length();
						break;
					}
					case CHANGE:
					{
						// Equivalent to DELETE followed by INSERT
						lengthOfSource += Strings.fromCodepoints(delta.getSource().getLines()).length();
						lengthOfTarget += Strings.fromCodepoints(delta.getTarget().getLines()).length();
						break;
					}
					default:
						throw new AssertionError(delta.getType().name());
				}
			}
			int result = Math.max(lengthOfSource, lengthOfTarget);
			// Trim text before the first delta and after the last delta
			result -= indexOfWordInStartDelta;
			AbstractDelta<Integer> lastDelta = deltas.get(deltas.size() - 1);
			String actual = Strings.fromCodepoints(lastDelta.getSource().getLines());
			result -= actual.length() - indexOfNextWordInEndDelta;
			return Math.max(0, result);
		}
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
				writer.writeEqual(Strings.fromCodepoints(delta.getSource().getLines()));
				break;
			}
			case DELETE:
			{
				writer.writeDeleted(Strings.fromCodepoints(delta.getSource().getLines()));
				break;
			}
			case INSERT:
			{
				writer.writeInserted(Strings.fromCodepoints(delta.getTarget().getLines()));
				break;
			}
			case CHANGE:
			{
				writer.writeDeleted(Strings.fromCodepoints(delta.getSource().getLines()));
				writer.writeInserted(Strings.fromCodepoints(delta.getTarget().getLines()));
				break;
			}
			default:
				throw new AssertionError("Unexpected type: " + delta.getType());
		}
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
