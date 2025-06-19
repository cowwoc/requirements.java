/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.message.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A diff writer that does not use ANSI escape codes.
 */
public final class TextOnly extends AbstractDiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	public static final String DIFF_PADDING = " ";
	private final Map<Integer, StringBuilder> lineToDiffLine = new HashMap<>();
	private final List<String> diffLines = new ArrayList<>();

	/**
	 * Creates a new text-only diff.
	 */
	public TextOnly()
	{
		super(DIFF_PADDING);
		addActualLine(0);
		addExpectedLine(0);
	}

	@Override
	protected void addActualLine(int number)
	{
		super.addActualLine(number);
		addDiffLine(number);
	}

	@Override
	protected void addExpectedLine(int number)
	{
		super.addExpectedLine(number);
		addDiffLine(number);
	}

	private void addDiffLine(int number)
	{
		lineToDiffLine.compute(number, (key, value) ->
		{
			if (value == null)
				value = new StringBuilder();
			return value;
		});
	}

	@Override
	public void writeEqual(String text)
	{
		if (flushed)
			throw new IllegalStateException("Writer was already flushed");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			lineToActualLine.get(actualLineNumber).append(line);
			int length = line.length();
			if (expectedLineNumber == actualLineNumber)
				lineToDiffLine.get(actualLineNumber).append(DiffConstants.DIFF_EQUAL.repeat(length));
			else
			{
				String paddingMarker = getPaddingMarker();
				lineToExpectedLine.get(actualLineNumber).append(paddingMarker.repeat(length));
				lineToDiffLine.get(actualLineNumber).append(DiffConstants.DIFF_EQUAL.repeat(length));

				lineToActualLine.get(expectedLineNumber).append(paddingMarker.repeat(length));
				lineToDiffLine.get(expectedLineNumber).append(DiffConstants.DIFF_EQUAL.repeat(length));
			}
			lineToExpectedLine.get(expectedLineNumber).append(line);
		});
	}

	@Override
	public void writeDeleted(String text)
	{
		if (flushed)
			throw new IllegalStateException("Writer was already flushed");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			lineToActualLine.get(actualLineNumber).append(line);
			int length = line.length();
			lineToDiffLine.get(actualLineNumber).append(DiffConstants.DIFF_DELETE.repeat(length));
			lineToExpectedLine.get(actualLineNumber).append(getPaddingMarker().repeat(length));
			lineToEqualLine.put(actualLineNumber, false);
		});
	}

	@Override
	public void writeInserted(String text)
	{
		if (flushed)
			throw new IllegalStateException("Writer was already flushed");
		if (text.isEmpty())
			return;
		splitLines(text, line ->
		{
			int length = line.length();
			lineToActualLine.get(expectedLineNumber).append(getPaddingMarker().repeat(length));
			lineToDiffLine.get(expectedLineNumber).append(DiffConstants.DIFF_INSERT.repeat(length));
			lineToExpectedLine.get(expectedLineNumber).append(line);
			lineToEqualLine.put(expectedLineNumber, false);
		});
	}

	@Override
	protected void beforeFlush()
	{
	}

	@Override
	protected void afterFlush()
	{
		this.diffLines.addAll(lineToDiffLine.entrySet().stream().
			sorted(Entry.comparingByKey()).
			map(Entry::getValue).map(StringBuilder::toString).toList());
	}

	@Override
	public List<String> getDiffLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be flushed");
		return diffLines;
	}
}