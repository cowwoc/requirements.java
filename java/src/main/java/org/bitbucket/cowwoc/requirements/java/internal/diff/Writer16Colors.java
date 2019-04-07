/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports 16 colors.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
public final class Writer16Colors extends AbstractColorWriter
{
	private static final String WHITE_FOREGROUND = "37;1";
	private static final String GRAY_FOREGROUND = "30;1";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "100";
	private static final String BLACK_BACKGROUND = "40";

	@Override
	public String getColorForKeep()
	{
		return PREFIX + GRAY_FOREGROUND + ";" + BLACK_BACKGROUND + POSTFIX;
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
