/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.message.diff;

import com.github.cowwoc.requirements10.java.GlobalConfiguration;
import com.github.cowwoc.requirements10.java.TerminalEncoding;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.InsertDelta;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generates a diff of two Strings.
 */
public final class DiffGenerator
{
	// A "word" is defined as one or more characters that are surrounded by word delimiters.
	//
	// \p{Zs} matches any unicode whitespace: https://www.regular-expressions.info/unicode.html
	private static final Pattern WORD_DELIMITER = getWordDelimiter();

	private static Pattern getWordDelimiter()
	{
		String whitespace = "\\p{Zs}+";
		String newline = "\r\n|[\r\n]";
		String specialCharacters = "[\\[\\](){}/\\\\*+\\-#:;.]";
		return Pattern.compile(whitespace + "|" +
			newline + "|" +
			specialCharacters);
	}

	private final TerminalEncoding encoding;
	private final String paddingMarker;
	private final SimplifyDeltas simplifyDeltas = new SimplifyDeltas();

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
	private String getPaddingMarker()
	{
		return switch (encoding)
		{
			case NONE -> TextOnly.DIFF_PADDING;
			case XTERM_8_COLORS -> Writer8Colors.DIFF_PADDING;
			case XTERM_16_COLORS -> Writer16Colors.DIFF_PADDING;
			case XTERM_256_COLORS -> Writer256Colors.DIFF_PADDING;
			case RGB_888_COLORS -> Writer16MillionColors.DIFF_PADDING;
		};
	}

	/**
	 * Generates the diff of two strings.
	 * <p>
	 * <b>NOTE</b>: Colors may be disabled when stdin or stdout are redirected. To override this
	 * behavior, use {@link GlobalConfiguration#terminalEncoding(TerminalEncoding)}.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @return the calculated diff
	 */
	public DiffResult diff(String actual, String expected)
	{
		// Mark the end of the string to guard against cases that end with whitespace
		List<Integer> actualWithEos = toCodepoints(actual + DiffConstants.EOS_MARKER);
		List<Integer> expectedWithEos = toCodepoints(expected + DiffConstants.EOS_MARKER);

		DiffWriter writer = createDiffWriter();
		// DiffUtils.diff() returns a list of deltas, where each delta is associated with a list of characters.
		List<AbstractDelta<Integer>> deltas = DiffUtils.diff(actualWithEos, expectedWithEos, true).
			getDeltas();
		simplifyDeltas.accept(deltas);
		for (AbstractDelta<Integer> delta : deltas)
			writeDelta(delta, writer);
		writer.flush();
		return new DiffResult(writer.getActualLines(), writer.getDiffLines(), writer.getExpectedLines(),
			writer.getEqualLines());
	}

	/**
	 * @param source a string
	 * @return a view of the string as a list of codepoints
	 */
	private static List<Integer> toCodepoints(String source)
	{
		return new AbstractList<>()
		{
			@Override
			public Integer get(int index)
			{
				return source.codePointAt(index);
			}

			@Override
			public int size()
			{
				return source.codePointCount(0, source.length());
			}
		};
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
				writer.writeEqual(fromCodepoints(delta.getSource().getLines()));
				break;
			}
			case DELETE:
			{
				writer.writeDeleted(fromCodepoints(delta.getSource().getLines()));
				break;
			}
			case INSERT:
			{
				writer.writeInserted(fromCodepoints(delta.getTarget().getLines()));
				break;
			}
			case CHANGE:
			{
				writer.writeDeleted(fromCodepoints(delta.getSource().getLines()));
				writer.writeInserted(fromCodepoints(delta.getTarget().getLines()));
				break;
			}
			default:
				throw new AssertionError("Unexpected type: " + delta.getType());
		}
	}

	/**
	 * @param codepoints String codepoints
	 * @return the {@code String} representation of the codepoints
	 */
	public static String fromCodepoints(List<Integer> codepoints)
	{
		int[] array = codepoints.stream().mapToInt(i -> i).toArray();
		return new String(array, 0, array.length);
	}

	/**
	 * @return a new writer
	 */
	private DiffWriter createDiffWriter()
	{
		return switch (encoding)
		{
			case NONE -> new TextOnly();
			case XTERM_8_COLORS -> new Writer8Colors();
			case XTERM_16_COLORS -> new Writer16Colors();
			case XTERM_256_COLORS -> new Writer256Colors();
			case RGB_888_COLORS -> new Writer16MillionColors();
		};
	}

	/**
	 * @param line a line
	 * @return true if {@code line} is empty once all colors and padding characters are removed
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
				line = line.replaceAll(Pattern.quote(DiffConstants.PREFIX) + ".+?" + Pattern.quote(
					DiffConstants.POSTFIX), "");
				break;
			}
			default:
				throw new AssertionError(encoding.name());
		}
		return containsOnly(line, paddingMarker);
	}

	/**
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return true if {@code source} only contains (potentially multiple) occurrences of {@code target} or if
	 * {@code source} is empty
	 */
	public static boolean containsOnly(String source, String target)
	{
		return source.isEmpty() || lastConsecutiveIndexOf(source, target) == 0;
	}

	/**
	 * Returns the index within {@code source} of the last consecutive occurrence of {@code target}. The last
	 * occurrence of the empty string {@code ""} is considered to occur at the index value
	 * {@code source.length()}.
	 * <p>
	 * The returned index is the largest value {@code k} for which {@code source.startsWith(target, k)}
	 * consecutively. If no such value of {@code k} exists, then {@code -1} is returned.
	 *
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return the index of the last consecutive occurrence of {@code target} in {@code source}, or {@code -1}
	 * if there is no such occurrence.
	 */
	public static int lastConsecutiveIndexOf(String source, String target)
	{
		assert (source != null) : "source may not be null";
		assert (target != null) : "target may not be null";
		int lengthOfTarget = target.length();
		int result = -1;
		if (lengthOfTarget == 0)
			return result;

		for (int i = source.length() - lengthOfTarget; i >= 0; i -= lengthOfTarget)
		{
			if (!source.startsWith(target, i))
				return result;
			result = i;
		}
		return result;
	}

	/**
	 * Improve the readability of diff by avoiding many diffs per word or short diffs in a short word.
	 * {@snippet lang = stdout:
	 *
	 * Good:
	 * -----=====-----
	 * -----+++++=====
	 * =====-----+++++
	 *
	 * Bad:
	 * =====-----=====-----
	 * +++++=====+++++=====
	 * -----++++++----=====
	 *
	 * Good:
	 * football
	 * ----====++++
	 *     ballroom
	 *
	 * Bad:
	 * 123
	 * -+=
	 * 133
	 *}
	 * <p>
	 * Bad deltas are replaced with a single {@code [DELETE actual, INSERT expected]} pair.
	 */
	private static final class SimplifyDeltas implements Consumer<List<AbstractDelta<Integer>>>
	{
		/**
		 * The deltas to process.
		 * <ul>
		 *   <li>A word may span one or more deltas.</li>
		 *   <li>The first delta it appears in is called the "start delta".</li>
		 *   <li>The last delta it appears in is called the "end delta".</li>
		 *   <li>Any deltas in between are called the "middle deltas".</li>
		 *   <li>If a word is fully contained within a single delta, its start and end deltas are the same, and
		 *   it has no middle deltas.</li>
		 * </ul>
		 */
		private List<AbstractDelta<Integer>> deltas;
		/**
		 * The index of the start delta in the list of all deltas.
		 */
		private int indexOfStartDelta;
		/**
		 * The index of the end delta in the list of all deltas.
		 */
		private int indexOfEndDelta;
		/**
		 * The index of the word in the start delta.
		 */
		private int startOfWord;
		/**
		 * The index right after the last character of the word in the end delta.
		 */
		private int endOfWord;
		/**
		 * The index of the next word in the end delta. If there are no more words, points to the end of the
		 * string.
		 */
		private int startOfNextWord;

		@Override
		public void accept(List<AbstractDelta<Integer>> deltas)
		{
			this.deltas = deltas;
			// We are looking for words that span multiple deltas. If the first delta contains multiple words, we
			// are interested in the last one.
			findFirstWord();
			if (indexOfStartDelta == deltas.size())
				return;
			do
			{
				findEndOfWord();
				updateDeltas();
			}
			while (findNextWord());
		}

		/**
		 * Finds the first word.
		 */
		private void findFirstWord()
		{
			// Words start after a whitespace delimiter within an EQUAL delta. If none is found, the start
			// of the first delta acts as a word boundary.
			AbstractDelta<Integer> delta = deltas.getFirst();
			indexOfStartDelta = 0;
			String actual = fromCodepoints(delta.getSource().getLines());
			MatchResult match = lastIndexOf(actual, WORD_DELIMITER).orElse(null);
			if (match == null)
				startOfWord = 0;
			else
				startOfWord = match.end();
		}

		/**
		 * Returns the last match returned by a regular expression.
		 *
		 * @param source the string to search within
		 * @param target the regular expression to search for
		 * @return the match result associated with the last occurrence of {@code target} in {@code source}
		 */
		public static Optional<MatchResult> lastIndexOf(String source, Pattern target)
		{
			assert (source != null) : "source may not be null";
			assert (target != null) : "target may not be null";
			Matcher matcher = target.matcher(source);
			Optional<MatchResult> matchResult = Optional.empty();
			while (matcher.find())
				matchResult = Optional.of(matcher.toMatchResult());
			return matchResult;
		}

		/**
		 * Finds the end of the word.
		 */
		private void findEndOfWord()
		{
			// Words end at a whitespace delimiter found within an EQUAL delta. If none is found, the end of the
			// last delta acts as a word boundary.
			for (int i = indexOfStartDelta + 1; i < deltas.size(); ++i)
			{
				AbstractDelta<Integer> delta = deltas.get(i);
				if (delta.getType() == DeltaType.EQUAL)
				{
					String actual = fromCodepoints(delta.getSource().getLines());
					Matcher matcher = WORD_DELIMITER.matcher(actual);
					if (matcher.find())
					{
						endOfWord = matcher.start();
						startOfNextWord = matcher.end();
						indexOfEndDelta = i;
						return;
					}
				}
			}
			indexOfEndDelta = deltas.size() - 1;
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
			if (numberOfUnequalDeltas(deltasInWord) <= 2 &&
				(shortestDelta(deltasInWord) >= 3 || longestWord(deltasInWord) >= 5))
			{
				// Diff is already good
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
			// Remove deltasInWord and insert updatedDeltas in its place
			deltasInWord.clear();
			deltasInWord.addAll(updatedDeltas);
			indexOfEndDelta -= deltasRemoved;
			startOfNextWord -= endOfWord;
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
			String actual = fromCodepoints(delta.getSource().getLines());
			String actualWord = actual.substring(startOfWord);
			List<Integer> codepointsBeforeActualWord = toCodepoints(actual.substring(0, startOfWord));

			String expectedWord;
			List<Integer> codepointsBeforeExpectedWord;
			if (delta.getType() == DeltaType.EQUAL)
			{
				expectedWord = actualWord;
				codepointsBeforeExpectedWord = codepointsBeforeActualWord;
			}
			else
			{
				expectedWord = fromCodepoints(delta.getTarget().getLines());
				codepointsBeforeExpectedWord = List.of();
			}
			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			if (startOfWord > 0)
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
				String actual = fromCodepoints(delta.getSource().getLines());

				String expected;
				if (delta.getType() == DeltaType.EQUAL)
					expected = actual;
				else
					expected = fromCodepoints(delta.getTarget().getLines());
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
			String actual = fromCodepoints(delta.getSource().getLines());
			String actualWord = actual.substring(0, endOfWord);

			// Extract the first word in the delta
			String expectedWord;
			if (delta.getType() == DeltaType.EQUAL)
				expectedWord = actualWord;
			else
			{
				String expected = fromCodepoints(delta.getTarget().getLines());
				expectedWord = expected.substring(0, endOfWord);
			}
			actualBuilder.append(actualWord);
			expectedBuilder.append(expectedWord);

			AbstractDelta<Integer> deleteActual = new DeleteDelta<>(
				new Chunk<>(delta.getSource().getPosition(), toCodepoints(actualBuilder.toString())),
				new Chunk<>(delta.getTarget().getPosition(), List.of()));
			AbstractDelta<Integer> insertExpected = new InsertDelta<>(
				new Chunk<>(delta.getSource().getPosition(), List.of()),
				new Chunk<>(delta.getTarget().getPosition(),
					toCodepoints(expectedBuilder.toString())));
			updatedDeltas.add(deleteActual);
			updatedDeltas.add(insertExpected);

			// Add the remaining part of the delta
			if (endOfWord < actual.length())
			{
				List<Integer> codepointsAfterActual = toCodepoints(actual.substring(endOfWord));
				List<Integer> codepointsAfterExpected;
				if (delta.getType() == DeltaType.EQUAL)
					codepointsAfterExpected = codepointsAfterActual;
				else
				{
					String expected = fromCodepoints(delta.getTarget().getLines());
					codepointsAfterExpected = toCodepoints(expected.substring(endOfWord));
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
			if (indexOfStartDelta == deltas.size() - 1)
				return false;

			// Similar logic as findFirstWord()
			AbstractDelta<Integer> delta = deltas.get(indexOfStartDelta);
			if (delta.getType() == DeltaType.EQUAL)
			{
				String actual = fromCodepoints(delta.getSource().getLines());
				MatchResult result = lastIndexOf(actual, WORD_DELIMITER).orElseThrow(() ->
					new AssertionError("Expecting result to be equal to " +
						"indexOfNextWordInEndDelta (" + startOfNextWord + ") or later.\n" +
						"actual: '" + actual + "'"));
				startOfWord = result.end();
			}
			return true;
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
						result = Math.min(result, fromCodepoints(delta.getSource().getLines()).length());
						break;
					}
					case INSERT:
					{
						result = Math.min(result, fromCodepoints(delta.getTarget().getLines()).length());
						break;
					}
					case CHANGE:
					{
						// Equivalent to DELETE followed by INSERT
						result = Math.min(result, fromCodepoints(delta.getSource().getLines()).length());
						result = Math.min(result, fromCodepoints(delta.getTarget().getLines()).length());
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
						lengthOfSource += fromCodepoints(delta.getSource().getLines()).length();
						break;
					}
					case INSERT:
					{
						lengthOfTarget += fromCodepoints(delta.getTarget().getLines()).length();
						break;
					}
					case CHANGE:
					{
						// Equivalent to DELETE followed by INSERT
						lengthOfSource += fromCodepoints(delta.getSource().getLines()).length();
						lengthOfTarget += fromCodepoints(delta.getTarget().getLines()).length();
						break;
					}
					default:
						throw new AssertionError(delta.getType().name());
				}
			}
			int result = Math.max(lengthOfSource, lengthOfTarget);
			// Trim text before the first delta and after the last delta
			result -= startOfWord;
			AbstractDelta<Integer> lastDelta = deltas.getLast();
			String actual = fromCodepoints(lastDelta.getSource().getLines());
			result -= actual.length() - startOfNextWord;
			return Math.max(0, result);
		}
	}
}