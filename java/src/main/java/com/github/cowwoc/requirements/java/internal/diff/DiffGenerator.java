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
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.InsertDelta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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
			List<WordWithDeltas> wordToDeltas = new WordToDeltaMapper().apply(deltas);
			writeWords(wordToDeltas, writer);
			writer.close();
			return new DiffResult(writer.getActualLines(), writer.getMiddleLines(), writer.getExpectedLines());
		}
		catch (DiffException e)
		{
			throw new AssertionError(e);
		}
	}

	/**
	 * Converts a mapping from deltas to words to a mapping from words to deltas.
	 */
	private static final class WordToDeltaMapper
		implements Function<List<AbstractDelta<Integer>>, List<WordWithDeltas>>
	{
		public final StringBuilder currentWord = new StringBuilder();
		public final List<AbstractDelta<Integer>> currentDeltas = new ArrayList<>();
		public final List<WordWithDeltas> result = new ArrayList<>();

		/**
		 * @param deltas a list of deltas, each delta is associated with one or more words
		 * @return a list of words, each word is associated with one or more deltas
		 */
		@Override
		public List<WordWithDeltas> apply(List<AbstractDelta<Integer>> deltas)
		{
			for (AbstractDelta<Integer> delta : deltas)
			{
				switch (delta.getType())
				{
					case EQUAL:
					{
						int sourcePosition = delta.getSource().getPosition();
						int targetPosition = delta.getTarget().getPosition();
						for (String word : Strings.splitPreserveDelimiter(
							Strings.fromCodepoints(delta.getSource().getLines()), WORDS))
						{
							boolean isDelimiter = WORDS.matcher(word).matches();
							if (isDelimiter)
								onDelimiter();
							List<Integer> wordCodepoints = Strings.toCodepoints(word);
							currentDeltas.add(new EqualDelta<>(
								new Chunk<>(sourcePosition, wordCodepoints),
								new Chunk<>(targetPosition, wordCodepoints)));
							sourcePosition += wordCodepoints.size();
							currentWord.append(word);
							if (isDelimiter)
								onDelimiter();
						}
						break;
					}
					case DELETE:
					{
						int sourcePosition = delta.getSource().getPosition();
						int targetPosition = delta.getTarget().getPosition();
						for (String word : Strings.splitPreserveDelimiter(
							Strings.fromCodepoints(delta.getSource().getLines()), WORDS))
						{
							sourcePosition = onDelete(word, sourcePosition, targetPosition);
						}
						break;
					}
					case INSERT:
					{
						int sourcePosition = delta.getSource().getPosition();
						int targetPosition = delta.getTarget().getPosition();
						for (String word : Strings.splitPreserveDelimiter(
							Strings.fromCodepoints(delta.getTarget().getLines()), WORDS))
						{
							targetPosition = onInsert(word, sourcePosition, targetPosition);
						}
						break;
					}
					case CHANGE:
					{
						// Add DELETE, INSERT deltas for each word found in "actual"
						int sourcePosition = delta.getSource().getPosition();
						int targetPosition = delta.getTarget().getPosition();
						List<String> toDelete = Strings.splitPreserveDelimiter(Strings.fromCodepoints(
							delta.getSource().getLines()), WORDS);
						String toInsert = Strings.fromCodepoints(delta.getTarget().getLines());
						int toInsertPosition = 0;
						for (String word : toDelete)
						{
							sourcePosition = onDelete(word, sourcePosition, targetPosition);
							int charactersToInsert = Math.min(word.length(),
								toInsert.length() - toInsertPosition);
							if (charactersToInsert > 0)
							{
								targetPosition = onInsert(toInsert.substring(toInsertPosition,
									toInsertPosition + charactersToInsert),
									sourcePosition, targetPosition);
								toInsertPosition += charactersToInsert;
							}
						}
						// Insert any remaining characters
						if (toInsertPosition < toInsert.length())
							onInsert(toInsert.substring(toInsertPosition), sourcePosition, targetPosition);
						break;
					}
				}
			}
			if (currentWord.length() > 0)
				result.add(new WordWithDeltas(currentWord.toString(), currentDeltas));
			return result;
		}

		/**
		 * Invoked when text should be deleted.
		 *
		 * @param word           the word to delete
		 * @param sourcePosition the source position associated with the delta
		 * @param targetPosition the target position associated with the delta
		 * @return the updated sourcePosition
		 */
		private int onDelete(String word, int sourcePosition, int targetPosition)
		{
			boolean isDelimiter = WORDS.matcher(word).matches();
			if (isDelimiter)
				onDelimiter();
			List<Integer> wordCodepoints = Strings.toCodepoints(word);
			currentDeltas.add(new DeleteDelta<>(
				new Chunk<>(sourcePosition, wordCodepoints),
				new Chunk<>(targetPosition, List.of())));
			sourcePosition += wordCodepoints.size();
			currentWord.append(word);
			if (isDelimiter)
				onDelimiter();
			return sourcePosition;
		}

		/**
		 * Invoked when text should be inserted.
		 *
		 * @param word           the word to delete
		 * @param sourcePosition the source position associated with the delta
		 * @param targetPosition the target position associated with the delta
		 * @return the updated targetPosition
		 */
		private int onInsert(String word, int sourcePosition, int targetPosition)
		{
			List<Integer> wordCodepoints = Strings.toCodepoints(word);
			currentDeltas.add(new InsertDelta<>(
				new Chunk<>(sourcePosition, List.of()),
				new Chunk<>(targetPosition, wordCodepoints)));
			targetPosition += wordCodepoints.size();
			return targetPosition;
		}

		/**
		 * Flushes words upon hitting a delimiter.
		 */
		private void onDelimiter()
		{
			// If the first word is a delimiter, there is nothing to flush.
			if (currentDeltas.isEmpty())
				return;
			result.add(new WordWithDeltas(currentWord.toString(), currentDeltas));
			currentDeltas.clear();
			currentWord.delete(0, currentWord.length());
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
	 * Writes words.
	 *
	 * @param words  a list of words associated with one or more deltas
	 * @param writer the writer to write into
	 */
	private void writeWords(List<WordWithDeltas> words, DiffWriter writer)
	{
		for (WordWithDeltas word : words)
		{
			if (numberOfUnequalDeltas(word.deltas) <= 2)
			{
				// If the word has 2 or less unequal deltas then provide a character-level granularity.

				// Good:
				// -----=====-----
				// -----+++++=====
				// =====-----+++++
				//
				// Bad:
				// =====-----=====-----
				// +++++=====+++++=====
				// -----++++++----=====
				writeWord(word, writer);
			}
			else
			{
				// Otherwise, provide a word-granularity.
				StringBuilder actual = new StringBuilder();
				StringBuilder expected = new StringBuilder();
				for (AbstractDelta<Integer> delta : word.deltas)
				{
					actual.append(Strings.fromCodepoints(delta.getSource().getLines()));
					expected.append(Strings.fromCodepoints(delta.getTarget().getLines()));
				}
				writer.writeDeleted(replaceNewlineWithMarker(actual));
				writer.writeInserted(replaceNewlineWithMarker(expected));
			}
		}
	}

	/**
	 * Write a single word.
	 *
	 * @param word   a word
	 * @param writer the writer to write into
	 */
	private void writeWord(WordWithDeltas word, DiffWriter writer)
	{
		for (AbstractDelta<Integer> delta : word.deltas)
		{
			switch (delta.getType())
			{
				case EQUAL:
				{
					String text = Strings.fromCodepoints(delta.getSource().getLines());
					writer.writeEqual(replaceNewlineWithMarker(text));
					break;
				}
				case DELETE:
				{
					String text = Strings.fromCodepoints(delta.getSource().getLines());
					writer.writeDeleted(replaceNewlineWithMarker(text));
					break;
				}
				case INSERT:
				{
					String text = Strings.fromCodepoints(delta.getTarget().getLines());
					writer.writeInserted(replaceNewlineWithMarker(text));
					break;
				}
				default:
					throw new AssertionError("Unexpected type: " + delta.getType());
			}
		}
	}

	/**
	 * Replaces the newline character with a {@code NEWLINE_MARKER}.
	 *
	 * @param text some text
	 * @return the updated text
	 */
	private static String replaceNewlineWithMarker(CharSequence text)
	{
		return NEWLINE_PATTERN.matcher(text).replaceAll(QUOTED_NEWLINE_MARKER);
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
