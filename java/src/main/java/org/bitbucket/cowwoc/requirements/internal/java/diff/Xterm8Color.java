/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.diff;

/**
 * An xterm terminal that supports an 8 color palette.
 */
public final class Xterm8Color extends AbstractXterm
{
	private static final String GRAY_FOREGROUND = "37";
	private static final String BLACK_FOREGROUND = "30";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "47";

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @throws NullPointerException if any of the arguments are null
	 */
	public Xterm8Color(String actual, String expected)
	{
		super(actual, expected);
	}

	@Override
	public String getColorForPadding()
	{
		return DiffConstants.PREFIX + BLACK_FOREGROUND + ";" + GRAY_BACKGROUND + DiffConstants.POSTFIX;
	}

	@Override
	public String getColorForInsert()
	{
		return DiffConstants.PREFIX + GRAY_FOREGROUND + ";" + GREEN_BACKGROUND + DiffConstants.POSTFIX;
	}

	@Override
	public String getColorForDelete()
	{
		return DiffConstants.PREFIX + GRAY_FOREGROUND + ";" + RED_BACKGROUND + DiffConstants.POSTFIX;
	}
}
