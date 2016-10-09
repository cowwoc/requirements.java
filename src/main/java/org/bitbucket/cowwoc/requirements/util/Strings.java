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
	 * Prevent construction.
	 */
	private Strings()
	{
	}
}
