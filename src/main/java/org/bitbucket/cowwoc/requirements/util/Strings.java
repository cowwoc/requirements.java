/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.util;

/**
 * String helper functions.
 *
 * @author Gili Tzabari
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
		throws NullPointerException
	{
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	/**
	 * @param str   a string
	 * @param times the number of times to repeat the string
	 * @return a string containing {@code str} repeated the specified number of times
	 * @throws NullPointerException     if {@code str} is null
	 * @throws IllegalArgumentException if {@code times} is negative
	 */
	public static String repeat(String str, int times)
		throws NullPointerException, IllegalArgumentException
	{
		if (str == null)
			throw new IllegalArgumentException("str may not be null");
		if (times < 0)
			throw new IllegalArgumentException("times may not be negative: " + times);
		if (times == 0)
			return "";
		StringBuilder result = new StringBuilder(str.length() * times);
		for (int i = 0; i < times; ++i)
			result.append(str);
		return result.toString();
	}

	/**
	 * Prevent construction.
	 */
	private Strings()
	{
	}
}
