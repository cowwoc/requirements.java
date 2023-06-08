/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports 16 colors.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
public final class Writer16Colors extends AbstractColorWriter
{
	private static final String WHITE_FOREGROUND = "37;1";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "100";

	/**
	 * Creates a new writer.
	 */
	public Writer16Colors()
	{
	}

	@Override
	public String decorateEqualText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + DEFAULT_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateDeletedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + RED_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateInsertedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + GREEN_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decoratePadding(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + GRAY_BACKGROUND + POSTFIX + text;
	}

	@Override
	protected void afterClose()
	{
	}
}