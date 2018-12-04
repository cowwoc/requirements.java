/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.diff;

/**
 * A terminal that supports a 24-bit color palette.
 *
 * @see <a href="https://gist.github.com/XVilka/8346728">https://gist.github.com/XVilka/8346728</a>
 */
public final class Rgb888Color extends AbstractXterm
{
	private static final String WHITE_FOREGROUND = "38;2;255;255;255";
	private static final String GRAY_FOREGROUND = "38;2;188;188;188";
	private static final String GREEN_BACKGROUND = "48;2;0;135;0";
	private static final String RED_BACKGROUND = "48;2;175;0;0";
	private static final String GRAY_BACKGROUND = "48;2;66;66;66";

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value
	 * @param expected the expected value
	 * @throws NullPointerException if any of the arguments are null
	 */
	public Rgb888Color(String actual, String expected)
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
