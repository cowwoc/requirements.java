/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import java.util.AbstractList;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String helper functions.
 */
public final class Strings
{
	/**
	 * A delimiter that denotes the beginning or end of a Java text block.
	 */
	public static final String TEXT_BLOCK_DELIMITER = ("\"").repeat(3);

	/**
	 * Prevent construction.
	 */
	private Strings()
	{
	}

	/**
	 * Capitalizes the first letter of each word.
	 *
	 * @param str a string
	 * @return a string with the first letter of each word capitalized
	 * @throws NullPointerException if {@code str} is null
	 */
	public static String capitalize(String str)
	{
		StringBuilder result = new StringBuilder(str.length());
		int codepoints = str.codePointCount(0, str.length());
		boolean firstLetter = true;
		for (int i = 0; i < codepoints; ++i)
		{
			int cp = str.codePointAt(i);
			if (Character.isLetter(cp))
			{
				if (firstLetter)
				{
					firstLetter = false;
					result.appendCodePoint(Character.toTitleCase(cp));
				}
				else
					result.appendCodePoint(Character.toLowerCase(cp));
				continue;
			}
			if (Character.isWhitespace(cp))
				firstLetter = true;
			result.appendCodePoint(cp);
		}
		return result.toString();
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
		Optional<MatchResult> result = Optional.empty();
		while (matcher.find())
			result = Optional.of(matcher.toMatchResult());
		return result;
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
	 * @param source a string
	 * @return a view of the string as a list of codepoints
	 */
	public static List<Integer> toCodepoints(String source)
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
	 * @param codepoints String codepoints
	 * @return the {@code String} representation of the codepoints
	 */
	public static String fromCodepoints(List<Integer> codepoints)
	{
		int[] array = codepoints.stream().mapToInt(i -> i).toArray();
		return new String(array, 0, array.length);
	}

	/**
	 * @param text      the {@code String} to align
	 * @param minLength the minimum length of {@code text}
	 * @return {@code text} padded on the right with spaces until its length is greater than or equal to
	 * {@code minLength}
	 */
	public static String alignLeft(String text, int minLength)
	{
		int actualLength = text.length();
		if (actualLength > minLength)
			return text;
		return text + " ".repeat(minLength - actualLength);
	}

	/**
	 * @param text a {@code String}
	 * @return the Java String representation of a {@code String}
	 */
	public static String asJavaString(String text)
	{
		if (needsEscaping(text))
		{
			if (text.contains("\n"))
				return asTextBlock(text);
			return escapeSingleLine(text);
		}
		return "\"" + text + "\"";
	}

	/**
	 * @param text a {@code String}
	 * @return true if {@code text} contains any characters that require escaping
	 */
	private static boolean needsEscaping(String text)
	{
		for (int codepoint : (Iterable<Integer>) text.codePoints()::iterator)
		{
			if (codepoint == '\t' || codepoint == '\b' || codepoint == '\n' || codepoint == '\r' ||
				codepoint == '\f' || codepoint == '\"' || codepoint == '\\')
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param text a {@code String}
	 * @return an escaped representation of {@code text}
	 */
	private static String escapeSingleLine(String text)
	{
		StringBuilder result = new StringBuilder(text.length() + 16);

		for (int codepoint : (Iterable<Integer>) text.codePoints()::iterator)
		{
			switch (codepoint)
			{
				case '\t' -> result.append("\\t");
				case '\b' -> result.append("\\b");
				case '\n' -> result.append("\\n");
				case '\r' -> result.append("\\r");
				case '\f' -> result.append("\\f");
				// No need to escape the ' character in a String: https://stackoverflow.com/a/16664166/14731
				case '\"' -> result.append("\\\"");
				case '\\' -> result.append("\\\\");
				default -> result.appendCodePoint(codepoint);
			}
		}
		result.insert(0, '"');
		result.insert(result.length(), '"');
		return result.toString();
	}

	/**
	 * @param text a {@code String}
	 * @return {@code text}'s representation as a text block
	 */
	private static String asTextBlock(String text)
	{
		// Escape textBlockDelimiter with a slash
		text = text.replaceAll("(?<!\\\\)\"\"\"", Matcher.quoteReplacement("\\" + TEXT_BLOCK_DELIMITER));
		// Lines that end with whitespace must replace the last whitespace character with \s
		text = text.replaceAll("\\p{Zs}$", Matcher.quoteReplacement("\\s"));
		// Text blocks that end with a quote character require a special end sequence:
		// """
		// "someQuotedText"\
		// """
		//
		// The slash at the end of the line indicates a line continuation. The text block then ends normally.
		if (text.endsWith("\""))
			text += "\\\n";

		return TEXT_BLOCK_DELIMITER + "\n" +
			text + TEXT_BLOCK_DELIMITER;
	}
}