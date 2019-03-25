/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.LINE_LENGTH;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.NEWLINE_PATTERN;

/**
 * Base implementation for all diff writers.
 */
abstract class AbstractDiffWriter implements DiffWriter
{
	protected final StringBuilder actualLine = new StringBuilder(LINE_LENGTH);
	protected final StringBuilder expectedLine = new StringBuilder(LINE_LENGTH);
	/**
	 * A padding character used to align values vertically.
	 */
	private final String paddingMarker;
	private final List<String> actualList = new ArrayList<>();
	private final List<String> expectedList = new ArrayList<>();
	/**
	 * The actual value. Set by {@link #close()}.
	 */
	private List<String> actual;
	/**
	 * The expected value. Set by {@link #close()}.
	 */
	private List<String> expected;
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
	 * Keeps a line of text in both {@code Actual} and {@code Expected}.
	 *
	 * @param line the line
	 */
	protected abstract void keepLine(String line);

	/**
	 * Inserts a line that is present in {@code Expected} but not {@code Actual}.
	 *
	 * @param line the text
	 */
	protected abstract void insertLine(String line);

	/**
	 * Deletes a line that is present in {@code Actual} but not {@code Expected}.
	 *
	 * @param line the text
	 */
	protected abstract void deleteLine(String line);

	/**
	 * Invoked before flushing each line.
	 */
	protected abstract void beforeFlushLine();

	/**
	 * Invoked after closing the writer.
	 */
	protected abstract void afterClose();

	@Override
	public String getPaddingMarker()
	{
		return paddingMarker;
	}

	@Override
	public void keep(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			keepLine(line);

			if (i < size - 1)
			{
				// (i == size - 1) does not necessarily indicate the end of a line
				flushLine();
			}
		}
	}

	@Override
	public void insert(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			if (line.length() > 0)
				insertLine(line);
			if (i < size - 1)
			{
				// (i == size - 1) does not necessarily indicate the end of a line
				flushLine();
			}
		}
	}

	@Override
	public void delete(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			if (line.length() > 0)
				deleteLine(line);
			if (i < size - 1)
			{
				// (i == size - 1) does not necessarily indicate the end of a line
				flushLine();
			}
		}
	}

	/**
	 * Flushes the contents of {@code actualLine}, {@code expectedLine} into {@code actualList},
	 * {@code expectedList}.
	 */
	protected void flushLine()
	{
		beforeFlushLine();
		String string = actualLine.toString();
		actualList.add(string);
		actualLine.delete(0, actualLine.length());

		string = expectedLine.toString();
		expectedList.add(string);
		expectedLine.delete(0, expectedLine.length());
	}

	@Override
	public void close()
	{
		if (closed)
			return;
		closed = true;
		flushLine();
		this.actual = Collections.unmodifiableList(actualList);
		this.expected = Collections.unmodifiableList(expectedList);
		afterClose();
	}

	@Override
	public List<String> getActual()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return actual;
	}

	@Override
	public List<String> getExpected()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return expected;
	}
}
