/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.annotations.Beta;
import java.util.regex.Pattern;

/**
 * Constants used for generating diffs.
 *
 * @author Gili Tzabari
 */
@Beta
final class DiffConstants
{
	/**
	 * A performance hint indicating the expected length of a line.
	 */
	public static final int LINE_LENGTH = 80;
	/**
	 * A pattern matching newline characters anywhere in a string.
	 */
	public static final Pattern NEWLINE_PATTERN = Pattern.compile("\r?\n");
	/**
	 * Character denoting a newline character.
	 */
	public static final String NEWLINE_MARKER = "\\n";
	/**
	 * Character denoting the end of string.
	 */
	public static final String EOS_MARKER = "\\0";
	/**
	 * Indicates a character is equal in the actual and expected values.
	 */
	public static final String DIFF_EQUAL = "=";
	/**
	 * Indicates a character to delete from the actual value.
	 */
	public static final String DIFF_DELETE = " ";
	/**
	 * Indicates a character to insert into the actual value.
	 */
	public static final String DIFF_INSERT = "^";
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
