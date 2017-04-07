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
	/**
	 * Returns the index within {@code source} of the last consecutive occurrence of {@code target}.
	 * The last occurrence of the empty string {@code ""} is considered to occur at the index value
	 * {@code source.length()}.
	 * <p>
	 * The returned index is the largest value {@code k} for which {@code source.startsWith(target, k)}
	 * consecutively. If no such value of {@code k} exists, then {@code -1} is returned.
	 *
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return the index of the last consecutive occurrence of {@code target} in {@code source}, or
	 *         {@code -1} if there is no such occurrence.
	 */
	static int lastConsecutiveIndexOf(String source, String target)
	{
		assert (source != null): "source may not be null";
		assert (target != null): "target may not be null";
		int lengthOfTarget = target.length();
		int result = -1;
		if (lengthOfTarget == 0)
			return result;

		for (int i = source.length() - lengthOfTarget; i >= 0; i -= lengthOfTarget)
		{
			if (!source.startsWith(target, i))
				return result;
			result = i;
		}
		return result;
	}
	/**
	 * A padding character used to align values vertically.
	 */
	protected final String paddingMarker;
	protected final StringBuilder actualLine;
	protected final StringBuilder expectedLine;
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
	 * @throws NullPointerException if any of the arguments are null
	 */
	AbstractDiffWriter(String actual, String expected, String paddingMarker)
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		if (paddingMarker == null)
			throw new NullPointerException("paddingMarker may not be null");
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
	 * Invoked before closing the writer.
	 */
	protected abstract void beforeClose();

	/**
	 * Invoked after closing the writer.
	 */
	protected abstract void afterClose();

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
		// Strip trailing whitespace to ensure that end of line markers are the last character.
		String string = actualLine.toString();
		int index = lastConsecutiveIndexOf(string, paddingMarker);
		if (index != -1)
			string = string.substring(0, index);
		actualList.add(string);
		actualLine.delete(0, actualLine.length());

		string = expectedLine.toString();
		index = lastConsecutiveIndexOf(string, paddingMarker);
		if (index != -1)
			string = string.substring(0, index);
		expectedList.add(string);
		expectedLine.delete(0, expectedLine.length());
	}

	@Override
	public void close()
	{
		if (closed)
			return;
		closed = true;
		beforeClose();
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
