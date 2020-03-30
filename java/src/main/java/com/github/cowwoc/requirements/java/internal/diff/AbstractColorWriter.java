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
	private boolean needToResetActual;
	private boolean needToResetExpected;

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
	public void writeUnchanged(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		if (text.isEmpty())
			return;
		actualLineBuilder.append(decorateUnchangedText(text));
		expectedLineBuilder.append(decorateUnchangedText(text));
		needToResetActual = true;
		needToResetExpected = true;
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
		actualLineBuilder.append(decoratePadding(length));
		expectedLineBuilder.append(decorateInsertedText(text));
		needToResetActual = true;
		needToResetExpected = true;
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
		actualLineBuilder.append(decorateDeletedText(text));
		expectedLineBuilder.append(decoratePadding(length));
		needToResetActual = true;
		needToResetExpected = true;
		if (text.endsWith(NEWLINE_MARKER))
			writeNewline();
	}

	@Override
	protected void beforeNewline()
	{
		if (needToResetActual)
		{
			actualLineBuilder.append(stopDecoration());
			needToResetActual = false;
		}
		if (needToResetExpected)
		{
			expectedLineBuilder.append(stopDecoration());
			needToResetExpected = false;
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
}
