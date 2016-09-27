/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.LINE_LENGTH;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_MARKER;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.NEWLINE_PATTERN;

/**
 * A diff representation that does not use ANSI escape codes.
 * <h3>Basic Rules</h3>
 * <ul>
 * <li>Space (&nbsp;) indicates characters that needs to be deleted from Actual.</li>
 * <li>Equal sign ({@code =}) indicates characters that are equal in Actual and Expected.</li>
 * <li>Up arrowhead ({@code ^}) indicates characters that needs to be inserted into Actual.</li>
 * </ul>
 * <h3>Example 1: insert</h3>
 * <pre>{@code
 * Actual   = ""
 * Expected = "text"
 * }</pre>results in the following diff:
 * <pre>{@code
 *
 * Actual  :
 * Diff    : ^^^^
 * Expected: text
 * }
 * </pre>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to insert "text".
 * <h3>Example 2: delete</h3>
 * <pre>{@code
 * Actual   = "text"
 * Expected = ""
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual  : text
 * Diff    :
 * Expected:
 * }
 * </pre>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to delete "text".
 * <h3>Example 3: padding</h3>
 * <pre>{@code
 * Actual   = "foo"
 * Expected = "   foo"
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual  :    foo
 * Diff    : ^^^===
 * Expected:    foo
 * }
 * </pre>
 * Meaning:
 * <ul>
 * <li>To go from {@code Actual} to {@code Expected} we need to insert three spaces at the beginning
 * of {@code Actual}.</li>
 * <li>There is no whitespace in {@code Expected} in front of "foo". This padding is used to line up
 * the strings vertically.</li>
 * </ul>
 * <h3>Example 4: delete, keep, insert</h3>
 * <pre>{@code
 * Actual   = "foosball"
 * Expected = "ballroom"
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual  : foosball
 * Diff    :     ====^^^^
 * Expected:     ballroom
 * }
 * </pre>
 * Meaning, we need to:
 * <ul>
 * <li>Delete "foos".</li>
 * <li>Keep "ball".</li>
 * <li>Insert "room".</li>
 * <li>There is no whitespace before "ballroom" or after "foosball". This padding is used to line up
 * the strings vertically.</li>
 * </ul>
 * <h3>Multi-line Strings</h3>
 * When comparing multi-line strings:
 * <ul>
 * <li>We display the diff on a per-line basis.</li>
 * <li>{@code Actual} and {@code Expected} are followed by a line number.</li>
 * <li>Lines that are identical (with the exception of the first and last line) are omitted.</li>
 * For example:
 * <pre>{@code
 *
 * Actual   = "first\nsecond\nfoo\forth\nfifth"
 * Expected = "first\nsecond\nbar\forth\nfifth"
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual@1  : first
 * Diff      : =====
 * Expected@1: first
 *
 * [...]
 *
 * Actual@3  : foo   \n
 * Diff      :    ^^^==
 * Expected@3:    bar\n
 *
 * [...]
 *
 * Actual@5  : fifth\0
 * Diff      : =======
 * Expected@5: fifth\0
 * }
 * </pre>
 * Meaning:
 * <ul>
 * <li>Lines 1-2 were equal.</li>
 * <li>On line 3, we need to delete "foo" and insert "bar".</li>
 * <li>Lines 4-5 were equal.</li>
 * </ul>
 * Lines always end with {@code \n} or {@code \0}. The former denotes a newline. The latter denotes
 * the end of the string.
 * <p>
 * Lines ending with "\n\n" or "\0\0" represents the literal string "\n" followed by a newline
 * character, or the literal string "\0" followed by the end of string, respectively.
 * <h3>Example 5: Missing Line Numbers</h3>
 * When {@code Actual} or {@code Expected} contain a line that does not have a corresponding line on
 * the other side we omit the latter's line number.
 * <pre>{@code
 * Actual   = "Foo\nBar"
 * Expected = "Bar"
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual@1  : Foo\n
 * Diff      :
 * Expected  :
 *
 * Actual@2  : Bar\0
 * Diff      : =====
 * Expected@1: Bar\0
 * }
 * </pre>
 * Meaning:
 * <ul>
 * <li>Actual contained more lines than Expected.</li>
 * <li>Expected did not have a line that corresponded to Actual line 1.</li>
 * <li>We need to delete line 1 and retain line 2 unchanged.</li>
 * </ul>
 *
 * @author Gili Tzabari
 */
final class TextOnly implements DiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	static final String PADDING_MARKER = " ";
	private final StringBuilder actualLine;
	private final StringBuilder expectedLine;
	private final StringBuilder middleLine;
	private final List<String> actualList;
	private final List<String> middleList;
	private final List<String> expectedList;
	private ImmutableList<String> actual;
	private ImmutableList<String> middle;
	private ImmutableList<String> expected;
	private boolean closed;

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value of a parameter
	 * @param expected the expected value of a parameter
	 * @throws NullPointerException if any of the arguments are null
	 */
	TextOnly(String actual, String expected)
		throws NullPointerException
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		this.actualLine = new StringBuilder(LINE_LENGTH);
		this.middleLine = new StringBuilder(LINE_LENGTH);
		this.expectedLine = new StringBuilder(LINE_LENGTH);
		this.actualList = new ArrayList<>(Math.max(1, actual.length() / LINE_LENGTH));
		this.middleList = new ArrayList<>(Math.max(1,
			Math.max(actual.length(), expected.length()) / LINE_LENGTH));
		this.expectedList = new ArrayList<>(Math.max(1, expected.length() / LINE_LENGTH));
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
			actualLine.append(line);
			middleLine.append(Strings.repeat("=", line.length()));
			expectedLine.append(line);

			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
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
					actualLine.append(Strings.repeat(PADDING_MARKER, length));;
				middleLine.append(Strings.repeat(DIFF_INSERT, length));
				expectedLine.append(line);
			}
			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
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
				actualLine.append(line);
				middleLine.append(Strings.repeat(DIFF_DELETE, length));
				if (i == size - 1)
					expectedLine.append(Strings.repeat(PADDING_MARKER, length));
			}
			if (i < size - 1)
			{
				// We're not sure whether the last line has actually ended
				flushLine();
			}
		}
	}

	/**
	 * Flushes the contents of {@code actualLine}, {@code middleLine}, {@code expectedLine} into
	 * {@code actualList}, {@code middleList}, {@code expectedList}.
	 */
	private void flushLine()
	{
		// Strip trailing whitespace to ensure that end of line markers are the last character.
		// See http://stackoverflow.com/a/16974310/14731 for the regex.
		actualList.add(actualLine.toString().replaceAll("\\s++$", ""));
		actualLine.delete(0, actualLine.length());

		middleList.add(middleLine.toString());
		middleLine.delete(0, middleLine.length());

		expectedList.add(expectedLine.toString().replaceAll("\\s++$", ""));
		expectedLine.delete(0, expectedLine.length());
	}

	@Override
	public void close()
	{
		if (closed)
			return;
		this.closed = true;
		flushLine();
		this.actual = ImmutableList.copyOf(actualList);
		this.expected = ImmutableList.copyOf(expectedList);
		this.middle = ImmutableList.copyOf(middleList);
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
