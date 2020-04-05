/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports 16 million colors.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 * @see <a href="https://gist.github.com/XVilka/8346728">https://gist.github.com/XVilka/8346728</a>
 */
public final class Writer16MillionColors extends AbstractColorWriter
{
	private static final String WHITE_FOREGROUND = "38;2;255;255;255";
	private static final String GRAY_FOREGROUND = "38;2;188;188;188";
	private static final String GREEN_BACKGROUND = "48;2;0;135;0";
	private static final String RED_BACKGROUND = "48;2;175;0;0";
	private static final String GRAY_BACKGROUND = "48;2;66;66;66";

	@Override
	public String decorateEqualText(String text)
	{
		return PREFIX + GRAY_FOREGROUND + POSTFIX + PREFIX + DEFAULT_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateDeletedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + RED_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateInsertedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + GREEN_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decoratePadding(String text)
	{
		return PREFIX + GRAY_FOREGROUND + POSTFIX + PREFIX + GRAY_BACKGROUND + POSTFIX + text;
	}
}
