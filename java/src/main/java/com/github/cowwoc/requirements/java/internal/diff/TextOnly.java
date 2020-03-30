/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.LINE_LENGTH;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;

/**
 * A diff writer that does not use ANSI escape codes.
 */
public final class TextOnly extends AbstractDiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	public static final String DIFF_PADDING = " ";
	private final StringBuilder middleLineBuilder = new StringBuilder(LINE_LENGTH);
	private final List<String> middleLinesBuilder = new ArrayList<>();
	private List<String> middleLines;

	public TextOnly()
	{
		super(DIFF_PADDING);
	}

	@Override
	public void writeUnchanged(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		int length = text.length();
		if (length == 0)
			return;
		actualLineBuilder.append(text);
		middleLineBuilder.append(DIFF_EQUAL.repeat(length));
		expectedLineBuilder.append(text);
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
		actualLineBuilder.append(getPaddingMarker().repeat(length));
		middleLineBuilder.append(DIFF_INSERT.repeat(length));
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
		actualLineBuilder.append(text);
		middleLineBuilder.append(DIFF_DELETE.repeat(length));
		expectedLineBuilder.append(getPaddingMarker().repeat(length));
		if (text.endsWith(NEWLINE_MARKER))
			writeNewline();
	}

	@Override
	protected void beforeNewline()
	{
	}

	@Override
	public void writeNewline()
	{
		super.writeNewline();
		middleLinesBuilder.add(middleLineBuilder.toString());
		middleLineBuilder.delete(0, middleLineBuilder.length());
	}

	@Override
	public String decoratePadding(int length)
	{
		return DIFF_PADDING.repeat(length);
	}

	@Override
	protected void afterClose()
	{
		this.middleLines = Collections.unmodifiableList(middleLinesBuilder);
	}

	@Override
	public List<String> getMiddleLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return middleLines;
	}
}