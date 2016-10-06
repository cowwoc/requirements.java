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
 * <h3>Basic Rules</h3>
 * <ul>
 * <li>Red characters need to be deleted from {@code Actual}.</li>
 * <li>Uncolored characters are equal in {@code Actual} and {@code Expected}.</li>
 * <li>Green characters need to be inserted into {@code Actual}.</li>
 * <li>Characters in the opposite direction of insertions and deletions are padded with {@code /}
 * characters to line up the strings vertically. This padding does not contribute any characters to
 * the string it is found in. Read on for concrete examples.</li>
 * </ul>
 * <h3>Example 1: insert</h3>
 * <pre>{@code
 * Actual   = ""
 * Expected = "text"
 * }</pre>results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example1.png" alt="diff output">
 * <p>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to insert "text".
 * <h3>Example 2: delete</h3>
 * <pre>{@code
 * Actual   = "text"
 * Expected = ""
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example2.png" alt="diff output">
 * <p>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to delete "text".
 * <h3>Example 3: padding</h3>
 * <pre>{@code
 * Actual   = "foo"
 * Expected = "   foo"
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example3.png" alt="diff output">
 * <p>
 * Meaning:
 * <ul>
 * <li>To go from {@code Actual} to {@code Expected} we need to insert three spaces at the beginning
 * of {@code Actual}.</li>
 * <li>There are no {@code ///} characters in {@code Expected} in front of "foo". This padding is
 * used to line up the strings vertically.</li>
 * </ul>
 * <h3>Example 4: delete, keep, insert</h3>
 * <pre>{@code
 * Actual   = "foosball"
 * Expected = "ballroom"
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example4.png" alt="diff output">
 * <p>
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
 * </ul>
 * For example:
 * <pre>{@code
 *
 * Actual   = "first\nsecond\nfoo\nforth\nfifth"
 * Expected = "first\nsecond\nbar\nforth\nfifth"
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example5.png" alt="diff output">
 * <p>
 * Meaning:
 * <ul>
 * <li>Lines 1-2 were equal.</li>
 * <li>On line 3, we need to delete "foo" and insert "bar".</li>
 * <li>Lines 4-5 were equal.</li>
 * </ul>
 * <p>
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
 * <p>
 * <img src="doc-files/xterm-example6.png" alt="diff output">
 * <p>
 * Meaning:
 * <ul>
 * <li>Actual contained more lines than Expected.</li>
 * <li>Expected did not have a line that corresponded to Actual line 1.</li>
 * <li>We need to delete line 1 and retain line 2 unchanged.</li>
 * </ul>
 *
 * @author Gili Tzabari
 */
abstract class AbstractXterm implements ColoredDiff
{
	/**
	 * A padding character used to align values vertically.
	 */
	private static final String PADDING_MARKER = "/";
	private final String colorForKeep;
	private final String colorForDelete;
	private final String colorForNeutral;
	private final String resetColor;
	private final StringBuilder actualLine;
	private final StringBuilder expectedLine;
	private final List<String> actualList;
	private final List<String> expectedList;
	private ImmutableList<String> actual;
	private ImmutableList<String> expected;
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
		this.colorForNeutral = getColorForKeep();
		this.colorForKeep = getColorForInsert();
		this.colorForDelete = getColorForDelete();
		this.resetColor = PREFIX + "0" + POSTFIX;
		this.actualLine = new StringBuilder(LINE_LENGTH);
		this.expectedLine = new StringBuilder(LINE_LENGTH);
		this.actualList = new ArrayList<>(Math.max(1, actual.length() / LINE_LENGTH));
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
				expectedLine.append(colorForKeep).append(line);
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
		int index = lastConsecutiveIndexOf(string, PADDING_MARKER);
		if (index != -1)
			string = string.substring(0, index);
		actualList.add(string);
		actualLine.delete(0, actualLine.length());

		string = expectedLine.toString();
		index = lastConsecutiveIndexOf(string, PADDING_MARKER);
		if (index != -1)
			string = string.substring(0, index);
		expectedList.add(string);
		expectedLine.delete(0, expectedLine.length());
	}

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
	private int lastConsecutiveIndexOf(String source, String target)
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
				return result + 1;
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
		return ImmutableList.of();
	}
}
