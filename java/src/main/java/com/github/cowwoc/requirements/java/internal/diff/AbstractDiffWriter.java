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
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_PATTERN;

/**
 * Base implementation for all diff writers.
 */
abstract class AbstractDiffWriter implements DiffWriter
{
	/**
	 * Maps each line number to its associated actual value.
	 */
	protected final Map<Integer, StringBuilder> lineToActualLine = new HashMap<>();
	/**
	 * Maps each line number to its associated expected value.
	 */
	protected final Map<Integer, StringBuilder> lineToExpectedLine = new HashMap<>();
	/**
	 * Maps each line number to an indication of whether the actual and expected values are equal.
	 */
	protected final Map<Integer, Boolean> lineToEqualLine = new HashMap<>();
	/**
	 * The current line number of the actual value.
	 */
	protected int actualLineNumber;
	/**
	 * The current line number of the expected value.
	 */
	protected int expectedLineNumber;
	/**
	 * A padding character used to align values vertically.
	 */
	private final String paddingMarker;
	/**
	 * The final list of lines in the actual value.
	 */
	private List<String> actualLines;
	/**
	 * The final list of lines in the expected value.
	 */
	private List<String> expectedLines;
	/**
	 * The final list that indicates which lines contain actual and expected values that are equal.
	 */
	private List<Boolean> equalLines;
	protected boolean flushed;

	/**
	 * @param paddingMarker a padding character used to align values vertically
	 * @throws NullPointerException     if any of the arguments are null
	 * @throws IllegalArgumentException if {@code paddingMarker} is empty
	 */
	AbstractDiffWriter(String paddingMarker)
	{
		if (paddingMarker == null)
			throw new NullPointerException("paddingMarker may not be null");
		if (paddingMarker.isEmpty())
			throw new IllegalArgumentException("paddingMarker may not be empty");
		this.paddingMarker = paddingMarker;
	}

	/**
	 * Invoked before closing the writer.
	 */
	protected abstract void beforeClose();

	/**
	 * Invoked after closing the writer.
	 */
	protected abstract void afterClose();

	@Override
	public String getPaddingMarker()
	{
		return paddingMarker;
	}

	/**
	 * Populates the state of lineTo* variables for a new actual line.
	 *
	 * @param number the line number to initialize
	 */
	protected void initActualLine(int number)
	{
		lineToActualLine.put(number, new StringBuilder());
		lineToEqualLine.putIfAbsent(actualLineNumber, true);
	}

	/**
	 * Populates the state of lineTo* variables for a new expected line.
	 *
	 * @param number the line number to initialize
	 */
	protected void initExpectedLine(int number)
	{
		lineToExpectedLine.put(number, new StringBuilder());
		lineToEqualLine.putIfAbsent(expectedLineNumber, true);
	}

	/**
	 * Splits text into one or more lines, invoking {@code writeNewline.run()} in place of each newline
	 * character.
	 *
	 * @param text         some text
	 * @param lineConsumer consumes one line at a time
	 * @throws IllegalStateException if the writer has already been flushed
	 */
	protected void splitLines(CharSequence text, Consumer<String> lineConsumer)
	{
		if (flushed)
			throw new IllegalStateException("Writer has already been flushed");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < lines.length; ++i)
		{
			boolean isLastLine = i == lines.length - 1;
			line.delete(0, line.length());
			line.append(lines[i]);
			if (!isLastLine)
				line.append(NEWLINE_MARKER);
			if (line.length() > 0)
				lineConsumer.accept(line.toString());
			if (!isLastLine)
			{
				writeActualNewline();
				writeExpectedNewline();
			}
		}
	}

	/**
	 * Ends the current line.
	 *
	 * @throws IllegalStateException if the writer has already been flushed
	 */
	protected void writeActualNewline()
	{
		if (flushed)
			throw new IllegalStateException("Writer has already been flushed");
		++actualLineNumber;
		initActualLine(actualLineNumber);
		if (!lineToExpectedLine.containsKey(actualLineNumber))
			initExpectedLine(actualLineNumber);
	}

	/**
	 * Ends the current line.
	 *
	 * @throws IllegalStateException if the writer has already been flushed
	 */
	protected void writeExpectedNewline()
	{
		if (flushed)
			throw new IllegalStateException("Writer has already been flushed");
		++expectedLineNumber;
		initExpectedLine(expectedLineNumber);
		if (!lineToActualLine.containsKey(expectedLineNumber))
			initActualLine(expectedLineNumber);
	}

	@Override
	public void flush()
	{
		if (flushed)
			return;
		flushed = true;
		beforeClose();
		this.actualLines = Collections.synchronizedList(lineToActualLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).map(StringBuilder::toString).
			collect(Collectors.toList()));
		this.expectedLines = Collections.synchronizedList(lineToExpectedLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).map(StringBuilder::toString).
			collect(Collectors.toList()));
		this.equalLines = Collections.synchronizedList(lineToEqualLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).collect(Collectors.toList()));
		afterClose();
	}

	@Override
	public List<String> getActualLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be closed");
		return actualLines;
	}

	@Override
	public List<String> getExpectedLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be closed");
		return expectedLines;
	}

	@Override
	public List<Boolean> getEqualLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be closed");
		return equalLines;
	}
}