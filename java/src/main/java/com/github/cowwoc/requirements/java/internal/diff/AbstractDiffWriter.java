/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.LINE_LENGTH;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_PATTERN;

/**
 * Base implementation for all diff writers.
 */
abstract class AbstractDiffWriter implements DiffWriter
{
	/**
	 * Builds a line of the actual value.
	 */
	protected final StringBuilder actualLineBuilder = new StringBuilder(LINE_LENGTH);
	/**
	 * Builds a line of the expected value.
	 */
	protected final StringBuilder expectedLineBuilder = new StringBuilder(LINE_LENGTH);
	/**
	 * A padding character used to align values vertically.
	 */
	private final String paddingMarker;
	/**
	 * Builds the list of lines in the actual value.
	 */
	private final List<String> actualLinesBuilder = new ArrayList<>();
	/**
	 * Builds the list of lines in the expected value.
	 */
	private final List<String> expectedLinesBuilder = new ArrayList<>();
	/**
	 * The final list of lines in the actual value.
	 */
	private List<String> actualLines;
	/**
	 * The final list of lines in the expected value.
	 */
	private List<String> expectedLines;
	protected boolean closed;

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
	 * Invoked before ending each line.
	 */
	protected abstract void beforeNewline();

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
	 * Splits text into one or more lines, invoking {@link #writeNewline()} in place of each newline character.
	 *
	 * @param text         some text
	 * @param lineConsumer consumes one line at a time
	 */
	protected void splitLines(CharSequence text, Consumer<String> lineConsumer)
	{
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
				writeNewline();
		}
	}

	/**
	 * Ends the current line.
	 */
	protected void writeNewline()
	{
		beforeNewline();
		actualLinesBuilder.add(actualLineBuilder.toString());
		actualLineBuilder.delete(0, actualLineBuilder.length());

		expectedLinesBuilder.add(expectedLineBuilder.toString());
		expectedLineBuilder.delete(0, expectedLineBuilder.length());
	}

	@Override
	public void close()
	{
		if (closed)
			return;
		closed = true;
		writeNewline();
		this.actualLines = Collections.unmodifiableList(actualLinesBuilder);
		this.expectedLines = Collections.unmodifiableList(expectedLinesBuilder);
		afterClose();
	}

	@Override
	public List<String> getActualLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return actualLines;
	}

	@Override
	public List<String> getExpectedLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return expectedLines;
	}
}
