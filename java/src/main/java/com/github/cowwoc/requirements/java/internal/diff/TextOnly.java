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

/**
 * A diff writer that does not use ANSI escape codes.
 */
public final class TextOnly extends AbstractDiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	public static final String DIFF_PADDING = " ";
	private final StringBuilder diffLineBuilder = new StringBuilder(LINE_LENGTH);
	private final List<String> diffLinesBuilder = new ArrayList<>();
	private List<String> diffLines;

	public TextOnly()
	{
		super(DIFF_PADDING);
	}

	@Override
	public void writeEqual(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			actualLineBuilder.append(line);
			diffLineBuilder.append(DIFF_EQUAL.repeat(line.length()));
			expectedLineBuilder.append(line);
		});
	}

	@Override
	public void writeDeleted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			actualLineBuilder.append(line);
			int length = line.length();
			diffLineBuilder.append(DIFF_DELETE.repeat(length));
			expectedLineBuilder.append(getPaddingMarker().repeat(length));
		});
	}

	@Override
	public void writeInserted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			int length = line.length();
			actualLineBuilder.append(getPaddingMarker().repeat(length));
			diffLineBuilder.append(DIFF_INSERT.repeat(length));
			expectedLineBuilder.append(line);
		});
	}

	@Override
	protected void beforeNewline()
	{
	}

	@Override
	public void writeNewline()
	{
		super.writeNewline();
		diffLinesBuilder.add(diffLineBuilder.toString());
		diffLineBuilder.delete(0, diffLineBuilder.length());
	}

	@Override
	protected void afterClose()
	{
		this.diffLines = Collections.unmodifiableList(diffLinesBuilder);
	}

	@Override
	public List<String> getDiffLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return diffLines;
	}
}