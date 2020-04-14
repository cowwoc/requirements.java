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
	 * Prevent construction.
	 */
	private Strings()
	{
	}
}
