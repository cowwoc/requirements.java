/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * A terminal that supports a 256 color palette.
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
	private static final String BLACK_BACKGROUND = "48;5;16";

	@Override
	public String decorateUnchangedText(String text)
	{
		return PREFIX + GRAY_FOREGROUND + ";" + BLACK_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateInsertedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + GREEN_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateDeletedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + POSTFIX + PREFIX + RED_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decoratePadding(int length)
	{
		return PREFIX + GRAY_FOREGROUND + POSTFIX + PREFIX + GRAY_BACKGROUND + POSTFIX +
			PADDING_MARKER.repeat(length);
	}
}
