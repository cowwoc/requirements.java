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
	// IntelliJ IDEA 2019.1 Darcula color scheme:
	// * inverts black/white colors
	// * Resets the background color to default even if only the foreground color is changed
	//
	// Therefore:
	// * We use the default background color instead of black/white which are unreliable.
	// * We always set the background color even if we only want to change the foreground color.
	// * We use a white foreground instead of gray to increase the contrast of the text.
	private static final String WHITE_FOREGROUND = "97";
	private static final String BLACK_FOREGROUND = "30";
	private static final String GREEN_BACKGROUND = "42";
	private static final String RED_BACKGROUND = "41";
	private static final String GRAY_BACKGROUND = "47";
	private static final String DEFAULT_BACKGROUND = "49";
	private static final String BLACK_BACKGROUND = "40";

	@Override
	public String decorateUnchangedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + BLACK_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateInsertedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + GREEN_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decorateDeletedText(String text)
	{
		return PREFIX + WHITE_FOREGROUND + ";" + RED_BACKGROUND + POSTFIX + text;
	}

	@Override
	public String decoratePadding(int length)
	{
		return PREFIX + BLACK_FOREGROUND + ";" + DEFAULT_BACKGROUND + POSTFIX +
			PADDING_MARKER.repeat(length);
	}
}
