/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message.diff;

import static io.github.cowwoc.requirements13.java.internal.message.diff.DiffConstants.POSTFIX;
import static io.github.cowwoc.requirements13.java.internal.message.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports 256 colors.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
public final class Writer256Colors extends AbstractColorWriter
{
	// OSX 10.9 renders color 15 as light gray while others render it as white. Codes 16-231 seem to
	// be more portable.
	private static final String WHITE_FOREGROUND = "38;5;231";
	private static final String GRAY_FOREGROUND = "38;5;250";
	private static final String GREEN_BACKGROUND = "48;5;28";
	private static final String RED_BACKGROUND = "48;5;124";
	private static final String GRAY_BACKGROUND = "48;5;244";

	/**
	 * Creates a new writer.
	 */
	public Writer256Colors()
	{
	}

	@Override
	public String decorateEqualText(String text)
	{
		return PREFIX + GRAY_FOREGROUND + ";" + DEFAULT_BACKGROUND + POSTFIX + text;
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

	@Override
	protected void afterFlush()
	{
	}
}