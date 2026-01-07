/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

import static io.github.cowwoc.requirements13.java.internal.message.diff.DiffConstants.LINEFEED_MARKER;

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
	 * A padding character used to align values vertically.
	 */
	private final String paddingMarker;
	/**
	 * The final list of lines in the actual value.
	 */
	private final List<String> actualLines = new ArrayList<>();
	/**
	 * The final list of lines in the expected value.
	 */
	private final List<String> expectedLines = new ArrayList<>();
	/**
	 * The final list that indicates which lines contain actual and expected values that are equal.
	 */
	private final List<Boolean> equalLines = new ArrayList<>();
	/**
	 * The current line number of the actual value.
	 */
	protected int actualLineNumber;
	/**
	 * The current line number of the expected value.
	 */
	protected int expectedLineNumber;
	/**
	 * {@code true} if the writer has been flushed.
	 */
	protected boolean flushed;

	/**
	 * @param paddingMarker a padding character used to align values vertically
	 * @throws NullPointerException     if {@code paddingMarker} is null
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
	 * Invoked before flushing the writer.
	 */
	protected abstract void beforeFlush();

	/**
	 * Invoked after flushing the writer.
	 */
	protected abstract void afterFlush();

	@Override
	public String getPaddingMarker()
	{
		return paddingMarker;
	}

	/**
	 * Adds a new line for the actual value.
	 *
	 * @param number the line number to add
	 */
	protected void addActualLine(int number)
	{
		lineToActualLine.put(number, new StringBuilder());
		lineToEqualLine.putIfAbsent(actualLineNumber, true);
	}

	/**
	 * Adds a new line for the expected value.
	 *
	 * @param number the line number to initialize
	 */
	protected void addExpectedLine(int number)
	{
		lineToExpectedLine.put(number, new StringBuilder());
		lineToEqualLine.putIfAbsent(expectedLineNumber, true);
	}

	/**
	 * Splits text into one or more lines.
	 *
	 * @param text         some text
	 * @param lineConsumer consumes one line at a time
	 * @throws IllegalStateException if the writer has already been flushed
	 */
	protected void splitLines(CharSequence text, Consumer<String> lineConsumer)
	{
		if (flushed)
			throw new IllegalStateException("Writer has already been flushed");
		String[] lines = DiffConstants.NEWLINE_PATTERN.split(text, -1);
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < lines.length; ++i)
		{
			boolean isLastLine = i == lines.length - 1;
			line.delete(0, line.length());
			line.append(lines[i].replace("\r", LINEFEED_MARKER));
			if (!isLastLine)
				line.append(DiffConstants.NEWLINE_MARKER);
			if (!line.isEmpty())
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
		addActualLine(actualLineNumber);
		if (!lineToExpectedLine.containsKey(actualLineNumber))
			addExpectedLine(actualLineNumber);
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
		addExpectedLine(expectedLineNumber);
		if (!lineToActualLine.containsKey(expectedLineNumber))
			addActualLine(expectedLineNumber);
	}

	@Override
	public void flush()
	{
		if (flushed)
			return;
		flushed = true;
		beforeFlush();
		this.actualLines.addAll(lineToActualLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).map(StringBuilder::toString).toList());
		this.expectedLines.addAll(lineToExpectedLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).map(StringBuilder::toString).toList());
		this.equalLines.addAll(lineToEqualLine.entrySet().stream().
			sorted(Entry.comparingByKey()).map(Entry::getValue).toList());
		afterFlush();
	}

	@Override
	public List<String> getActualLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be flushed");
		return actualLines;
	}

	@Override
	public List<String> getExpectedLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be flushed");
		return expectedLines;
	}

	@Override
	public List<Boolean> getEqualLines()
	{
		if (!flushed)
			throw new IllegalStateException("Writer must be flushed");
		return equalLines;
	}
}