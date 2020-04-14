/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;

/**
 * A diff writer that does not use ANSI escape codes.
 */
public final class TextOnly extends AbstractDiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	public static final String DIFF_PADDING = " ";
	private final Map<Integer, StringBuilder> lineToDiffBuilder = new HashMap<>();
	private List<String> diffLines;

	public TextOnly()
	{
		super(DIFF_PADDING);
		initActualLine(0);
		initExpectedLine(0);
	}

	@Override
	protected void initActualLine(int number)
	{
		super.initActualLine(number);
		initDiffLine(number);
	}

	@Override
	protected void initExpectedLine(int number)
	{
		super.initExpectedLine(number);
		initDiffLine(number);
	}

	private void initDiffLine(int number)
	{
		lineToDiffBuilder.compute(number, (key, value) ->
		{
			if (value == null)
				value = new StringBuilder();
			return value;
		});
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
			lineToActualLine.get(actualLineNumber).append(line);
			if (expectedLineNumber == actualLineNumber)
				lineToDiffBuilder.get(actualLineNumber).append(DIFF_EQUAL.repeat(line.length()));
			else
			{
				int length = line.length();
				lineToExpectedLine.get(actualLineNumber).append(getPaddingMarker().repeat(length));
				lineToDiffBuilder.get(actualLineNumber).append(DIFF_DELETE.repeat(length));

				lineToActualLine.get(expectedLineNumber).append(getPaddingMarker().repeat(length));
				lineToDiffBuilder.get(expectedLineNumber).append(DIFF_INSERT.repeat(length));
			}
			lineToExpectedLine.get(expectedLineNumber).append(line);
		}, () ->
		{
			writeActualNewline();
			writeExpectedNewline();
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
			lineToActualLine.get(actualLineNumber).append(line);
			int length = line.length();
			lineToDiffBuilder.get(actualLineNumber).append(DIFF_DELETE.repeat(length));
			lineToExpectedLine.get(actualLineNumber).append(getPaddingMarker().repeat(length));
		}, this::writeActualNewline);
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
			lineToActualLine.get(expectedLineNumber).append(getPaddingMarker().repeat(length));
			lineToDiffBuilder.get(expectedLineNumber).append(DIFF_INSERT.repeat(length));
			lineToExpectedLine.get(expectedLineNumber).append(line);
		}, this::writeExpectedNewline);
	}

	@Override
	protected void beforeClose()
	{
	}

	@Override
	protected void afterClose()
	{
		this.diffLines = Collections.synchronizedList(lineToDiffBuilder.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).map(StringBuilder::toString).
			collect(Collectors.toList()));
	}

	@Override
	public List<String> getDiffLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return diffLines;
	}
}