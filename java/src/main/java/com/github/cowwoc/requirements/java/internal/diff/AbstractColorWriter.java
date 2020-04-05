/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.Collections;
import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * Base implementation for all color diff writers.
 *
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
abstract class AbstractColorWriter extends AbstractDiffWriter
	implements ColoredDiff
{
	/**
	 * A padding character used to align values vertically.
	 */
	protected static final String DIFF_PADDING = "/";
	protected static final String DEFAULT_BACKGROUND = "49";
	private DecorationType actualDecoration = DecorationType.UNDECORATED;
	private DecorationType expectedDecoration = DecorationType.UNDECORATED;

	protected AbstractColorWriter()
	{
		super(DIFF_PADDING);
	}

	@Override
	public String stopDecoration()
	{
		return PREFIX + "0" + POSTFIX;
	}

	@Override
	public void writeEqual(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		if (text.isEmpty())
			return;
		if (actualDecoration != DecorationType.EQUAL)
		{
			actualLineBuilder.append(decorateUnchangedText(text));
			actualDecoration = DecorationType.EQUAL;
		}
		else
			actualLineBuilder.append(text);

		if (expectedDecoration != DecorationType.EQUAL)
		{
			expectedLineBuilder.append(decorateUnchangedText(text));
			expectedDecoration = DecorationType.EQUAL;
		}
		else
			expectedLineBuilder.append(text);
		if (text.endsWith(NEWLINE_MARKER))
			writeNewline();
	}

	@Override
	public void writeDeleted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		int length = text.length();
		if (length == 0)
			return;
		if (actualDecoration != DecorationType.DELETE)
		{
			actualLineBuilder.append(decorateDeletedText(text));
			actualDecoration = DecorationType.DELETE;
		}
		else
			actualLineBuilder.append(text);

		String padding = getPaddingMarker().repeat(length);
		if (expectedDecoration != DecorationType.DELETE)
		{
			expectedLineBuilder.append(decoratePadding(padding));
			expectedDecoration = DecorationType.DELETE;
		}
		else
			expectedLineBuilder.append(padding);
		if (text.endsWith(NEWLINE_MARKER))
			writeNewline();
	}

	@Override
	public void writeInserted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		int length = text.length();
		if (length == 0)
			return;

		String padding = getPaddingMarker().repeat(length);
		if (actualDecoration != DecorationType.INSERT)
		{
			actualLineBuilder.append(decoratePadding(padding));
			actualDecoration = DecorationType.INSERT;
		}
		else
			actualLineBuilder.append(decoratePadding(padding));

		if (expectedDecoration != DecorationType.INSERT)
		{
			expectedLineBuilder.append(decorateInsertedText(text));
			expectedDecoration = DecorationType.INSERT;
		}
		else
			expectedLineBuilder.append(text);
		if (text.endsWith(NEWLINE_MARKER))
			writeNewline();
	}

	@Override
	protected void beforeNewline()
	{
		if (actualDecoration != DecorationType.UNDECORATED)
		{
			actualLineBuilder.append(stopDecoration());
			actualDecoration = DecorationType.UNDECORATED;
		}
		if (expectedDecoration != DecorationType.UNDECORATED)
		{
			expectedLineBuilder.append(stopDecoration());
			expectedDecoration = DecorationType.UNDECORATED;
		}
	}

	@Override
	protected void afterClose()
	{
	}

	@Override
	public List<String> getMiddleLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return Collections.emptyList();
	}

	/**
	 * Possible types of decorations.
	 */
	public enum DecorationType
	{
		UNDECORATED,
		DELETE,
		INSERT,
		EQUAL
	}
}
