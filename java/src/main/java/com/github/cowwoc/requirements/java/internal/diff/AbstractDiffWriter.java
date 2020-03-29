/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.LINE_LENGTH;

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
