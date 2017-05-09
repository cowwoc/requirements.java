/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.internal.core.diff.DiffConstants.NEWLINE_PATTERN;
import static org.bitbucket.cowwoc.requirements.internal.core.util.ConsoleConstants.LINE_LENGTH;

/**
 * Base implementation for all diff writers.
 *
 * @author Gili Tzabari
 */
abstract class AbstractDiffWriter implements DiffWriter
{
	protected final StringBuilder actualLine;
	protected final StringBuilder expectedLine;
	/**
	 * A padding character used to align values vertically.
	 */
	private final String paddingMarker;
	private final List<String> actualList;
	private final List<String> expectedList;
	private List<String> actual;
	private List<String> expected;
	protected boolean closed;

	/**
	 * Creates a new instance.
	 *
	 * @param actual        the actual value
	 * @param expected      the expected value
	 * @param paddingMarker a padding character used to align values vertically
	 * @throws NullPointerException     if any of the arguments are null
	 * @throws IllegalArgumentException if {@code paddingMarker} is empty
	 */
	AbstractDiffWriter(String actual, String expected, String paddingMarker)
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		if (paddingMarker == null)
			throw new NullPointerException("paddingMarker may not be null");
		if (paddingMarker.isEmpty())
			throw new IllegalArgumentException("paddingMarker may not be empty");
		this.paddingMarker = paddingMarker;
		this.actualLine = new StringBuilder(LINE_LENGTH);
		this.expectedLine = new StringBuilder(LINE_LENGTH);
		this.actualList = new ArrayList<>(Math.max(1, actual.length() / LINE_LENGTH));
		this.expectedList = new ArrayList<>(Math.max(1, expected.length() / LINE_LENGTH));
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
	 * @param length the length of the padding
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
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getActual()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return actual;
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getExpected()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return expected;
	}
}
