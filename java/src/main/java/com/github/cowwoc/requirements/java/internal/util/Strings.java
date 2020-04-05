/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String helper functions.
 */
public final class Strings
{
	/**
	 * @param str        a string
	 * @param prefix     a prefix
	 * @param ignoreCase {@code true} if case should be ignored when comparing characters
	 * @return true if {@code start} starts with {@code prefix}, disregarding case sensitivity
	 * @throws NullPointerException if any of the arguments are null
	 */
	public static boolean startsWith(String str, String prefix, boolean ignoreCase)
	{
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
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
	 * Returns the index within {@code source} of the last consecutive occurrence of {@code target}.
	 * The last occurrence of the empty string {@code ""} is considered to occur at the index value
	 * {@code source.length()}.
	 * <p>
	 * The returned index is the largest value {@code k} for which {@code source.startsWith(target, k)}
	 * consecutively. If no such value of {@code k} exists, then {@code -1} is returned.
	 *
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return the index of the last consecutive occurrence of {@code target} in {@code source}, or
	 * {@code -1} if there is no such occurrence.
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
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return true if {@code source} only contains (potentially multiple) occurrences of {@code target} or if
	 * {@code source} is empty
	 */
	public static boolean containsOnly(String source, String target)
	{
		return lastConsecutiveIndexOf(source, target) == 0;
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
	 * Splits a string preserving the delimiters in the result.
	 *
	 * <pre>{@code
	 *   splitPreserveDelimiter("a,b,c", Pattern.compile(","))
	 * }</pre>
	 * yields
	 * <br>
	 * <pre>{@code
	 *   ["a", ",", "b", ",", "c"]
	 * }</pre>
	 *
	 * @param text       a string
	 * @param delimiters the pattern of delimiters
	 * @return the tokens and delimiters in {@code text}
	 */
	public static List<String> splitPreserveDelimiter(String text, Pattern delimiters)
	{
		List<String> list = new ArrayList<>();
		Matcher matcher = delimiters.matcher(text);
		int endOfLastMatch = 0;
		while (matcher.find())
		{
			if (endOfLastMatch < matcher.start())
			{
				String token = text.substring(endOfLastMatch, matcher.start());
				list.add(token);
			}
			String delimiter = matcher.group();
			list.add(delimiter);
			endOfLastMatch = matcher.end();
		}
		if (endOfLastMatch < text.length())
		{
			String token = text.substring(endOfLastMatch);
			list.add(token);
		}
		return list;
	}

	/**
	 * Prevent construction.
	 */
	private Strings()
	{
	}
}
