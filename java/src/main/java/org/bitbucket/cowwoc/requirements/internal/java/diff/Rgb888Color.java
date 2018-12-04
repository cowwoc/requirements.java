/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.diff;

import static org.bitbucket.cowwoc.requirements.internal.java.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.internal.java.diff.DiffConstants.PREFIX;

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
		return PREFIX + GRAY_FOREGROUND + POSTFIX + PREFIX + GRAY_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForInsert()
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + GREEN_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForDelete()
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + RED_BACKGROUND + POSTFIX;
	}
}
