/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.util;

import com.google.common.annotations.Beta;

/**
 * String helper functions.
 *
 * @author Gili Tzabari
 */
@Beta
public final class Strings
{
	/**
	 * @param str        a string
	 * @param prefix     a prefix
	 * @param ignoreCase {@code true} if case should be ignored when comparing characters
	 * @return true if {@code start} starts with {@code prefix}, disregarding case sensitivity
	 */
	public static boolean startsWith(String str, String prefix, boolean ignoreCase)
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
