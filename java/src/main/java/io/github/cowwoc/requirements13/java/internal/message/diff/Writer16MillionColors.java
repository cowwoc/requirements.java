/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message.diff;

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

	/**
	 * Creates a new writer.
	 */
	public Writer16MillionColors()
	{
	}

	@Override
	public String decorateEqualText(String text)
	{
		return DiffConstants.PREFIX + GRAY_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX +
			DEFAULT_BACKGROUND + DiffConstants.POSTFIX + text;
	}

	@Override
	public String decorateDeletedText(String text)
	{
		return DiffConstants.PREFIX + WHITE_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX +
			RED_BACKGROUND + DiffConstants.POSTFIX + text;
	}

	@Override
	public String decorateInsertedText(String text)
	{
		return DiffConstants.PREFIX + WHITE_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX +
			GREEN_BACKGROUND + DiffConstants.POSTFIX + text;
	}

	@Override
	public String decoratePadding(String text)
	{
		return DiffConstants.PREFIX + GRAY_FOREGROUND + DiffConstants.POSTFIX + DiffConstants.PREFIX +
			GRAY_BACKGROUND + DiffConstants.POSTFIX + text;
	}

	@Override
	protected void afterFlush()
	{
	}
}