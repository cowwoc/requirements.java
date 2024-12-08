/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.message.diff;

import java.util.regex.Pattern;

/**
 * Constants used for generating diffs.
 */
public final class DiffConstants
{
	/**
	 * Character denoting a linefeed character.
	 */
	public static final String LINEFEED_MARKER = "\\r";
	/**
	 * Character denoting a newline character.
	 */
	public static final String NEWLINE_MARKER = "\\n";
	/**
	 * Character denoting the end of string.
	 */
	public static final String EOS_MARKER = "\\0";
	/**
	 * A pattern matching newline characters.
	 */
	public static final Pattern NEWLINE_PATTERN = Pattern.compile("\r?\n");
	/**
	 * A pattern matching the end of a line or stream.
	 */
	public static final Pattern EOL_PATTERN = Pattern.compile("\\\\n|\\\\0$");
	/**
	 * Indicates that a character is equal in the actual and expected values.
	 */
	public static final String DIFF_EQUAL = " ";
	/**
	 * Indicates that a character needs to be removed from the actual value.
	 */
	public static final String DIFF_DELETE = "-";
	/**
	 * Indicates that a character needs to be added to the actual value.
	 */
	public static final String DIFF_INSERT = "+";
	/**
	 * The prefix for all ANSI sequences.
	 */
	public static final String PREFIX = "\033[";
	/**
	 * The postfix for all color sequences.
	 */
	public static final String POSTFIX = "m";

	/**
	 * Prevent construction.
	 */
	private DiffConstants()
	{
	}
}