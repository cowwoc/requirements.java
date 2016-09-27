/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.LINE_LENGTH;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_PATTERN;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.PREFIX;

/**
 * Base implementation for all XTerm terminals.
 *
 * @author Gili Tzabari
 */
abstract class AbstractXterm implements ColoredDiff
{
	/**
	 * A padding character used to align values vertically.
	 */
	private static final String PADDING_MARKER = "/";
	private final StringBuilder actualLine;
	private final StringBuilder expectedLine;
	private final List<String> actualList;
	private final List<String> expectedList;
	private ImmutableList<String> actual;
	private ImmutableList<String> expected;
	private ImmutableList<String> middle;
	private final String colorForInsert;
	private final String colorForDelete;
	private final String colorForNeutral;
	private final String resetColor;
	private boolean needToResetColor;
	private boolean closed;

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value of a parameter
	 * @param expected the expected value of a parameter
	 * @throws NullPointerException if any of the arguments are null
	 */
	protected AbstractXterm(String actual, String expected)
		throws NullPointerException
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		this.actualLine = new StringBuilder(LINE_LENGTH);
		this.expectedLine = new StringBuilder(LINE_LENGTH);
		this.actualList = new ArrayList<>(Math.max(1, actual.length() / LINE_LENGTH));
		this.expectedList = new ArrayList<>(Math.max(1, expected.length() / LINE_LENGTH));
		this.colorForNeutral = getColorForNeutral();
		this.colorForInsert = getColorForInsert();
		this.colorForDelete = getColorForDelete();
		this.resetColor = PREFIX + "0" + POSTFIX;
	}

	@Override
	public void keep(String text) throws IllegalStateException
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			actualLine.append(resetColor).append(line);
			expectedLine.append(resetColor).append(line);

			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
		needToResetColor = false;
	}

	@Override
	public void insert(String text) throws IllegalStateException
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			int length = line.length();
			if (length > 0)
			{
				if (i == size - 1)
					actualLine.append(colorForNeutral).append(Strings.repeat(PADDING_MARKER, length));
				expectedLine.append(colorForInsert).append(line);
			}
			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
		needToResetColor = true;
	}

	@Override
	public void delete(String text) throws IllegalStateException
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		String[] lines = NEWLINE_PATTERN.split(text, -1);
		for (int i = 0, size = lines.length; i < size; ++i)
		{
			String line = lines[i];
			if (i < size - 1)
				line += NEWLINE_MARKER;
			int length = line.length();
			if (length > 0)
			{
				actualLine.append(colorForDelete).append(line);
				if (i == size - 1)
					expectedLine.append(colorForNeutral).append(Strings.repeat(PADDING_MARKER, line.length()));
			}
			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
		needToResetColor = true;
	}

	/**
	 * Flushes the contents of {@code actualLine}, {@code expectedLine} into {@code actualList},
	 * {@code expectedList}.
	 */
	private void flushLine()
	{
		// Strip trailing whitespace to ensure that end of line markers are the last character.
		// See http://stackoverflow.com/a/16974310/14731 for the regex.
		String string = actualLine.toString();
		int index = lastIndexNotOf(string, PADDING_MARKER);
		if (index != -1)
			string = string.substring(0, index);
		actualList.add(string);
		actualLine.delete(0, actualLine.length());

		string = actualLine.toString();
		index = lastIndexNotOf(string, PADDING_MARKER);
		if (index != -1)
			string = string.substring(0, index);
		expectedList.add(string);
		expectedLine.delete(0, expectedLine.length());
	}

	/**
	 * @param source the string to search within
	 * @param target the string to search for
	 * @return the last index of {@code source} that does not start with {@code target}; -1 if
	 *         {@code source} starts with {@code target}
	 */
	private int lastIndexNotOf(String source, String target)
	{
		int length = target.length();
		if (length == 0)
			return -1;

		// Check for worse-case scenario
		if (source.startsWith(target))
			return -1;

		// Check the rest of the string
		for (int result = source.length() - length; result >= length; result -= length)
			if (!source.startsWith(target, result))
				return result;
		return -1;
	}

	@Override
	public void close()
	{
		if (closed)
			return;
		closed = true;
		if (needToResetColor)
		{
			actualLine.append(resetColor);
			expectedLine.append(resetColor);
		}
		flushLine();
		this.actual = ImmutableList.copyOf(actualList);
		this.expected = ImmutableList.copyOf(expectedList);
		List<String> temp = new ArrayList<>(actual.size());
		for (int i = 0, size = actual.size(); i < size; ++i)
			temp.add("");
		this.middle = ImmutableList.copyOf(temp);
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getActual() throws IllegalStateException
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return actual;
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getExpected() throws IllegalStateException
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return expected;
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getMiddle() throws IllegalStateException
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return middle;
	}
}
