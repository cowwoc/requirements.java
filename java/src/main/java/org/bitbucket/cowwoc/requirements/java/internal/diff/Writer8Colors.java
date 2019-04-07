/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports an 8 colors.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
public final class Writer8Colors extends AbstractColorWriter
{
	private static final String GRAY_FOREGROUND = "37";
	private static final String BLACK_FOREGROUND = "30";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "47";
	private static final String BLACK_BACKGROUND = "40";

	@Override
	public String getColorForKeep()
	{
		return PREFIX + GRAY_FOREGROUND + ";" + BLACK_BACKGROUND + POSTFIX;
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
