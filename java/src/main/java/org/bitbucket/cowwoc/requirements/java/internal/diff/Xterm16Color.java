/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * An xterm terminal that supports a 16 color palette. Adds "bold" and "aixterm" extensions on top
 * of {@link Xterm8Color}.
 */
public final class Xterm16Color extends AbstractXterm
{
	// On Ubuntu 16.04 and Netbeans 8.1, if we omit ;0 then the bold attribute of the previous
	// character gets used.
	private static final String WHITE_FOREGROUND = "37;1";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	// The standard colors (white on gray) are hard to read but not all terminals support aixterm.
	// Terminals that do not support aixterm will fallback to the basic colors.
	private static final String GRAY_BACKGROUND = "47;100";

	/**
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @throws NullPointerException if any of the arguments are null
	 */
	public Xterm16Color(String actual, String expected)
	{
		super(actual, expected);
	}

	@Override
	public String getColorForPadding()
	{
		return PREFIX + WHITE_FOREGROUND + ";" + GRAY_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForInsert()
	{
		return PREFIX + WHITE_FOREGROUND + ";" + GREEN_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForDelete()
	{
		return PREFIX + WHITE_FOREGROUND + ";" + RED_BACKGROUND + POSTFIX;
	}
}
