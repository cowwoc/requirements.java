/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

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
		return PREFIX + BLACK_FOREGROUND + ";" + GRAY_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForInsert()
	{
		return PREFIX + GRAY_FOREGROUND + ";" + GREEN_BACKGROUND + POSTFIX;
	}

	@Override
	public String getColorForDelete()
	{
		return PREFIX + GRAY_FOREGROUND + ";" + RED_BACKGROUND + POSTFIX;
	}
}
