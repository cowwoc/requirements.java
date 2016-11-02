/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.PREFIX;

/**
 * An xterm terminal that supports a 256-color palette.
 *
 * @author Gili Tzabari
 */
final class XTerm256Color extends AbstractXterm
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
	 * @param actual   the actual value of a parameter
	 * @param expected the expected value of a parameter
	 * @throws NullPointerException if any of the arguments are null
	 */
	XTerm256Color(String actual, String expected)
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
