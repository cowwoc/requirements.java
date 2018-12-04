/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.diff;

/**
 * An xterm terminal that supports a 256 color palette.
 */
public final class Xterm256Color extends AbstractXterm
{
	// OSX 10.9 renders color 15 as light gray while others render it as white. Codes 16-231 seem to
	// be more portable.
	private static final String WHITE_FOREGROUND = "38;5;231";
	private static final String GRAY_FOREGROUND = "38;5;250";
	private static final String GREEN_BACKGROUND = "48;5;28";
	private static final String RED_BACKGROUND = "48;5;124";
	private static final String GRAY_BACKGROUND = "48;5;244";

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @throws NullPointerException if any of the arguments are null
	 */
	public Xterm256Color(String actual, String expected)
	{
		super(actual, expected);
	}

	@Override
	public String getColorForPadding()
	{
		return DiffConstants.PREFIX + GRAY_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX + GRAY_BACKGROUND + DiffConstants.POSTFIX;
	}

	@Override
	public String getColorForInsert()
	{
		return DiffConstants.PREFIX + WHITE_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX + GREEN_BACKGROUND + DiffConstants.POSTFIX;
	}

	@Override
	public String getColorForDelete()
	{
		return DiffConstants.PREFIX + WHITE_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX + RED_BACKGROUND + DiffConstants.POSTFIX;
	}
}
