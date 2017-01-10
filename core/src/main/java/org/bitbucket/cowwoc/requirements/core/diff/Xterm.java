/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.PREFIX;

/**
 * An xterm terminal that supports an 8-color palette.
 *
 * @author Gili Tzabari
 */
final class Xterm extends AbstractXterm
{
	private static final String WHITE_FOREGROUND = "37;1";
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
	Xterm(String actual, String expected)
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
