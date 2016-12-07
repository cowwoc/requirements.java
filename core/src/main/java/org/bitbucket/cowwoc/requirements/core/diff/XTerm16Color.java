/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.diff;

import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.core.diff.DiffConstants.PREFIX;

/**
 * An xterm terminal that supports a 16-color palette.
 *
 * @author Gili Tzabari
 */
final class XTerm16Color extends AbstractXterm
{
	// On Ubuntu 16.04 and Netbeans 8.1, if we omit ;0 then the bold attribute of the previous
	// character gets used.
	private static final String GRAY_FOREGROUND = "37;0";
	private static final String WHITE_FOREGROUND = "37;1";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "100";

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value of a parameter
	 * @param expected the expected value of a parameter
	 * @throws NullPointerException if any of the arguments are null
	 */
	XTerm16Color(String actual, String expected)
	{
		super(actual, expected);
	}

	@Override
	public String getColorForPadding()
	{
		return PREFIX + GRAY_FOREGROUND + ";" + GRAY_BACKGROUND + POSTFIX;
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
