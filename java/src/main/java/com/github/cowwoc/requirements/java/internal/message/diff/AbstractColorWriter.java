/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.message.diff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static com.github.cowwoc.requirements.java.internal.message.diff.AbstractColorWriter.DecorationType.UNDECORATED;

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
	/**
	 * Maps from a line number in the actual value to the decoration at the end of the line.
	 */
	private final Map<Integer, DecorationType> lineToActualDecoration = new HashMap<>();
	/**
	 * Maps from a line number in the expected value to the decoration at the end of the line.
	 */
	private final Map<Integer, DecorationType> lineToExpectedDecoration = new HashMap<>();

	protected AbstractColorWriter()
	{
		super(DIFF_PADDING);
		addActualLine(0);
		addExpectedLine(0);
	}

	@Override
	protected final void addActualLine(int number)
	{
		super.addActualLine(number);
		lineToActualDecoration.put(number, UNDECORATED);
	}

	@Override
	protected final void addExpectedLine(int number)
	{
		super.addExpectedLine(number);
		lineToExpectedDecoration.put(number, UNDECORATED);
	}

	@Override
	public String stopDecoration()
	{
		return DiffConstants.PREFIX + "0" + DiffConstants.POSTFIX;
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
			DecorationType actualDecoration = lineToActualDecoration.get(actualLineNumber);
			if (actualDecoration == DecorationType.EQUAL)
				lineToActualLine.get(actualLineNumber).append(line);
			else
			{
				lineToActualLine.get(actualLineNumber).append(decorateEqualText(line));
				lineToActualDecoration.put(actualLineNumber, DecorationType.EQUAL);
			}

			if (expectedLineNumber != actualLineNumber)
			{
				int length = line.length();
				String padding = decoratePadding(getPaddingMarker().repeat(length));
				lineToExpectedLine.get(actualLineNumber).append(padding);
				lineToExpectedDecoration.put(actualLineNumber, DecorationType.EQUAL);

				lineToActualLine.get(expectedLineNumber).append(padding);
				lineToActualDecoration.put(expectedLineNumber, DecorationType.EQUAL);
			}

			DecorationType expectedDecoration = lineToExpectedDecoration.get(expectedLineNumber);
			if (expectedDecoration == DecorationType.EQUAL)
				lineToExpectedLine.get(expectedLineNumber).append(line);
			else
			{
				lineToExpectedLine.get(expectedLineNumber).append(decorateEqualText(line));
				lineToExpectedDecoration.put(expectedLineNumber, DecorationType.EQUAL);
			}
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
			DecorationType actualDecoration = lineToActualDecoration.get(actualLineNumber);
			if (actualDecoration == DecorationType.DELETE)
				lineToActualLine.get(actualLineNumber).append(line);
			else
			{
				lineToActualLine.get(actualLineNumber).append(decorateDeletedText(line));
				lineToActualDecoration.put(actualLineNumber, DecorationType.DELETE);
			}

			DecorationType expectedDecoration = lineToExpectedDecoration.get(actualLineNumber);
			String padding = getPaddingMarker().repeat(line.length());
			if (expectedDecoration == DecorationType.DELETE)
				lineToExpectedLine.get(actualLineNumber).append(padding);
			else
			{
				lineToExpectedLine.get(actualLineNumber).append(decoratePadding(padding));
				lineToExpectedDecoration.put(actualLineNumber, DecorationType.DELETE);
			}
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
			DecorationType actualDecoration = lineToActualDecoration.get(expectedLineNumber);
			String padding = getPaddingMarker().repeat(line.length());
			if (actualDecoration == DecorationType.INSERT)
				lineToActualLine.get(expectedLineNumber).append(decoratePadding(padding));
			else
			{
				lineToActualLine.get(expectedLineNumber).append(decoratePadding(padding));
				lineToActualDecoration.put(expectedLineNumber, DecorationType.INSERT);
			}

			DecorationType expectedDecoration = lineToExpectedDecoration.get(expectedLineNumber);
			if (expectedDecoration == DecorationType.INSERT)
				lineToExpectedLine.get(expectedLineNumber).append(line);
			else
			{
				lineToExpectedLine.get(expectedLineNumber).append(decorateInsertedText(line));
				lineToExpectedDecoration.put(expectedLineNumber, DecorationType.INSERT);
			}
			lineToEqualLine.put(expectedLineNumber, false);
		});
	}

	@Override
	protected void beforeFlush()
	{
		for (Entry<Integer, DecorationType> entry : lineToActualDecoration.entrySet())
		{
			if (entry.getValue() != UNDECORATED)
			{
				lineToActualLine.get(entry.getKey()).append(stopDecoration());
				entry.setValue(UNDECORATED);
			}
		}
		for (Entry<Integer, DecorationType> entry : lineToExpectedDecoration.entrySet())
		{
			if (entry.getValue() != UNDECORATED)
			{
				lineToExpectedLine.get(entry.getKey()).append(stopDecoration());
				entry.setValue(UNDECORATED);
			}
		}
	}

	@Override
	public List<String> getDiffLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be flushed");
		return List.of();
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