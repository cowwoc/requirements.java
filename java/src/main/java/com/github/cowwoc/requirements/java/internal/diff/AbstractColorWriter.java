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
import java.util.function.BiFunction;

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
	private static final BiFunction<Integer, DecorationType, DecorationType> DEFAULT_DECORATION =
		(key, value) ->
		{
			if (value == null)
				value = DecorationType.UNDECORATED;
			return value;
		};
	private final Map<Integer, DecorationType> lineToActualDecoration = new HashMap<>();
	private final Map<Integer, DecorationType> lineToExpectedDecoration = new HashMap<>();

	protected AbstractColorWriter()
	{
		super(DIFF_PADDING);
		initActualLine(0);
		initExpectedLine(0);
	}

	@Override
	protected void initActualLine(int number)
	{
		super.initActualLine(number);
		lineToActualDecoration.compute(number, DEFAULT_DECORATION);
	}

	@Override
	protected void initExpectedLine(int number)
	{
		super.initExpectedLine(number);
		lineToExpectedDecoration.compute(number, DEFAULT_DECORATION);
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
		splitLines(text, line ->
		{
			DecorationType actualDecoration = lineToActualDecoration.get(actualLineNumber);
			if (actualDecoration != DecorationType.EQUAL)
			{
				lineToActualLine.get(actualLineNumber).append(decorateEqualText(line));
				lineToActualDecoration.put(actualLineNumber, DecorationType.EQUAL);
			}
			else
				lineToActualLine.get(actualLineNumber).append(line);

			DecorationType expectedDecoration = lineToExpectedDecoration.get(expectedLineNumber);
			if (expectedLineNumber != actualLineNumber)
			{
				int length = line.length();
				String paddingMarker = getPaddingMarker();
				lineToExpectedLine.get(actualLineNumber).append(decoratePadding(paddingMarker.repeat(length)));
				lineToExpectedDecoration.put(actualLineNumber, DecorationType.EQUAL);

				lineToActualLine.get(expectedLineNumber).append(decoratePadding(paddingMarker.repeat(length)));
				lineToActualDecoration.put(expectedLineNumber, DecorationType.EQUAL);
			}

			if (expectedDecoration != DecorationType.EQUAL)
			{
				lineToExpectedLine.get(expectedLineNumber).append(decorateEqualText(line));
				lineToExpectedDecoration.put(expectedLineNumber, DecorationType.EQUAL);
			}
			else
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
			DecorationType actualDecoration = lineToActualDecoration.get(actualLineNumber);
			if (actualDecoration != DecorationType.DELETE)
			{
				lineToActualLine.get(actualLineNumber).append(decorateDeletedText(line));
				lineToActualDecoration.put(actualLineNumber, DecorationType.DELETE);
			}
			else
				lineToActualLine.get(actualLineNumber).append(line);

			DecorationType expectedDecoration = lineToExpectedDecoration.get(expectedLineNumber);
			String padding = getPaddingMarker().repeat(line.length());
			if (expectedDecoration != DecorationType.DELETE)
			{
				lineToExpectedLine.get(expectedLineNumber).append(decoratePadding(padding));
				lineToExpectedDecoration.put(expectedLineNumber, DecorationType.DELETE);
			}
			else
				lineToExpectedLine.get(expectedLineNumber).append(padding);
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
			DecorationType actualDecoration = lineToActualDecoration.get(actualLineNumber);
			String padding = getPaddingMarker().repeat(line.length());
			if (actualDecoration != DecorationType.INSERT)
			{
				lineToActualLine.get(actualLineNumber).append(decoratePadding(padding));
				lineToActualDecoration.put(actualLineNumber, DecorationType.INSERT);
			}
			else
				lineToActualLine.get(actualLineNumber).append(decoratePadding(padding));

			DecorationType expectedDecoration = lineToExpectedDecoration.get(expectedLineNumber);
			if (expectedDecoration != DecorationType.INSERT)
			{
				lineToExpectedLine.get(expectedLineNumber).append(decorateInsertedText(line));
				lineToExpectedDecoration.put(expectedLineNumber, DecorationType.INSERT);
			}
			else
				lineToExpectedLine.get(expectedLineNumber).append(line);
		}, this::writeExpectedNewline);
	}

	@Override
	protected void beforeClose()
	{
		for (Entry<Integer, DecorationType> entry : lineToActualDecoration.entrySet())
		{
			if (entry.getValue() != DecorationType.UNDECORATED)
			{
				lineToActualLine.get(entry.getKey()).append(stopDecoration());
				lineToActualDecoration.put(entry.getKey(), DecorationType.UNDECORATED);
			}
		}
		for (Entry<Integer, DecorationType> entry : lineToExpectedDecoration.entrySet())
		{
			if (entry.getValue() != DecorationType.UNDECORATED)
			{
				lineToExpectedLine.get(entry.getKey()).append(stopDecoration());
				lineToExpectedDecoration.put(entry.getKey(), DecorationType.UNDECORATED);
			}
		}
	}

	@Override
	protected void afterClose()
	{
	}

	@Override
	public List<String> getDiffLines()
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
