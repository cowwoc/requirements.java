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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
	public static final Pattern WORDS = Pattern.compile("\\s+|[.\\[\\](){}/\\\\*+\\-#]");
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
		List<String> actualWithEos = List.of(actual + EOS_MARKER);
		List<String> expectedWithEos = List.of(expected + EOS_MARKER);

		try
		{
			DiffWriter writer = createDiffWriter();
			List<AbstractDelta<String>> deltas = DiffUtils.diff(actualWithEos, expectedWithEos).getDeltas();
			deltas = addEqualDeltas(deltas, actualWithEos, expectedWithEos);
			writeDeltas(deltas, writer);
			writer.close();
			return new DiffResult(writer.getActualLines(), writer.getMiddleLines(), writer.getExpectedLines());
		}
		catch (DiffException e)
		{
			throw new AssertionError(e);
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
	 * Writes deltas that span multiple lines.
	 *
	 * @param deltas the deltas
	 * @param writer the writer to write into
	 */
	private void writeDeltas(List<AbstractDelta<String>> deltas, DiffWriter writer) throws DiffException
	{
		for (AbstractDelta<String> delta : deltas)
		{
			switch (delta.getType())
			{
				case EQUAL:
				{
					writeComponents(writer::writeUnchanged, delta.getSource().getLines());
					break;
				}
				case INSERT:
				{
					writeComponents(writer::writeInserted, delta.getTarget().getLines());
					break;
				}
				case DELETE:
				{
					writeComponents(writer::writeDeleted, delta.getSource().getLines());
					break;
				}
				case CHANGE:
				{
					Chunk<String> source = delta.getSource();
					List<String> actualWords = new ArrayList<>(source.size());
					for (String line : source.getLines())
						actualWords.addAll(splitWordsPreserveDelimiter(line));
					Chunk<String> target = delta.getTarget();
					List<String> expectedWords = new ArrayList<>(target.size());
					for (String line : target.getLines())
						expectedWords.addAll(splitWordsPreserveDelimiter(line));
					List<AbstractDelta<String>> wordDeltas = DiffUtils.diff(actualWords, expectedWords,
						Object::equals).getDeltas();
					wordDeltas = addEqualDeltas(wordDeltas, actualWords, expectedWords);
					writeWords(wordDeltas, writer);
					break;
				}
				default:
					throw new AssertionError(delta.getType().name());
			}
		}
	}

	/**
	 * Splits a string on word delimiters, preserving the delimiters in the result.
	 *
	 * @param text a string
	 * @return the words and delimiters in {@code text}
	 */
	protected static List<String> splitWordsPreserveDelimiter(String text)
	{
		List<String> list = new ArrayList<>();
		Matcher matcher = DiffGenerator.WORDS.matcher(text);
		int endOfLastMatch = 0;
		while (matcher.find())
		{
			if (endOfLastMatch < matcher.start())
			{
				String word = text.substring(endOfLastMatch, matcher.start());
				list.add(word);
			}
			String delimiter = matcher.group();
			list.add(delimiter);
			endOfLastMatch = matcher.end();
		}
		if (endOfLastMatch < text.length())
		{
			String word = text.substring(endOfLastMatch);
			list.add(word);
		}
		return list;
	}

	/**
	 * Writes deltas that span multiple lines.
	 *
	 * @param deltas one delta per word
	 * @param writer the writer to write into
	 */
	private void writeWords(List<AbstractDelta<String>> deltas, DiffWriter writer) throws DiffException
	{
		for (AbstractDelta<String> delta : deltas)
		{
			switch (delta.getType())
			{
				case EQUAL:
				{
					writeComponents(writer::writeUnchanged, delta.getSource().getLines());
					break;
				}
				case INSERT:
				{
					writeComponents(writer::writeInserted, delta.getTarget().getLines());
					break;
				}
				case DELETE:
				{
					writeComponents(writer::writeDeleted, delta.getSource().getLines());
					break;
				}
				case CHANGE:
				{
					List<Integer> actualCodepoints = toCodepoints(delta.getSource().getLines());
					List<Integer> expectedCodepoints = toCodepoints(delta.getTarget().getLines());

					List<AbstractDelta<Integer>> codepointDeltas = DiffUtils.diff(actualCodepoints, expectedCodepoints).
						getDeltas();
					codepointDeltas = addEqualDeltas(codepointDeltas, actualCodepoints, expectedCodepoints);
					if (numberOfUnequalDeltas(codepointDeltas) > 2)
					{
						// Provide character-level deltas so long as there are only two INSERT, DELETE deltas

						// Good:
						// -----=====-----
						// -----+++++=====
						// =====-----+++++
						//
						// Bad:
						// =====-----=====-----
						// +++++=====+++++=====
						// -----++++++----=====
						writeComponents(writer::writeDeleted, delta.getSource().getLines());
						writeComponents(writer::writeInserted, delta.getTarget().getLines());
					}
					else
						writeCodepoints(codepointDeltas, writer);
					break;
				}
				default:
					throw new AssertionError(delta.getType().name());
			}
		}
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
				case CHANGE:
				{
					// Equivalent to DELETE followed by INSERT
					result += 2;
					break;
				}
				case DELETE:
				case INSERT:
				{
					++result;
					break;
				}
				case EQUAL:
					break;
				default:
					throw new AssertionError(delta.getType().name());
			}
		}
		return result;
	}

	/**
	 * @param words a list of words
	 * @return a list of codepoints making up the string
	 */
	private List<Integer> toCodepoints(List<String> words)
	{
		StringBuilder joiner = new StringBuilder();
		for (String word : words)
			joiner.append(word);
		return joiner.toString().codePoints().boxed().collect(Collectors.toList());
	}

	/**
	 * Writes deltas that span multiple lines.
	 *
	 * @param deltas one delta per codepoint
	 * @param writer the writer to write into
	 */
	private void writeCodepoints(List<AbstractDelta<Integer>> deltas, DiffWriter writer)
	{
		for (AbstractDelta<Integer> delta : deltas)
		{
			switch (delta.getType())
			{
				case EQUAL:
				{
					String word = codepointsToString(delta.getSource().getLines());
					writeComponents(writer::writeUnchanged, List.of(word));
					break;
				}
				case INSERT:
				{
					String word = codepointsToString(delta.getTarget().getLines());
					writeComponents(writer::writeInserted, List.of(word));
					break;
				}
				case DELETE:
				{
					String word = codepointsToString(delta.getSource().getLines());
					writeComponents(writer::writeDeleted, List.of(word));
					break;
				}
				case CHANGE:
				{
					String deleted = codepointsToString(delta.getSource().getLines());
					String inserted = codepointsToString(delta.getTarget().getLines());
					writeComponents(writer::writeDeleted, List.of(deleted));
					writeComponents(writer::writeInserted, List.of(inserted));
					break;
				}
				default:
					throw new AssertionError(delta.getType().name());
			}
		}
	}

	/**
	 * @param codepoints String codepoints
	 * @return the {@code String} representation of the codepoints
	 */
	private static String codepointsToString(List<Integer> codepoints)
	{
		int[] array = codepoints.stream().mapToInt(i -> i).toArray();
		return new String(array, 0, array.length);
	}

	/**
	 * Writes one or more components, appending a newline at the end of all lines except for the last one.
	 *
	 * @param operator   the operator to invoke for each line
	 * @param components the components to process
	 */
	private static void writeComponents(Consumer<String> operator, List<String> components)
	{
		StringBuilder joiner = new StringBuilder();
		for (String component : components)
			joiner.append(component);
		String[] lines = NEWLINE_PATTERN.split(joiner.toString(), -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String text = lines[i];
			if (i != size - 1)
				text += NEWLINE_MARKER;
			operator.accept(text);
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
