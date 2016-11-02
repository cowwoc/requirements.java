/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import java.util.Collections;
import java.util.List;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.diff.string.DiffConstants.PREFIX;
import org.bitbucket.cowwoc.requirements.util.Strings;

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
abstract class AbstractXterm extends AbstractDiffWriter
	implements ColoredDiff
{
	/**
	 * A padding character used to align values vertically.
	 */
	static final String PADDING_MARKER = "/";
	final String paddingColor;
	final String insertColor;
	final String deleteColor;
	final String resetColor;
	private boolean needToResetActual;
	private boolean needToResetExpected;

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the actual value of a parameter
	 * @param expected the expected value of a parameter
	 * @throws NullPointerException if any of the arguments are null
	 */
	protected AbstractXterm(String actual, String expected)
	{
		super(actual, expected, PADDING_MARKER);
		this.paddingColor = getColorForPadding();
		this.insertColor = getColorForInsert();
		this.deleteColor = getColorForDelete();
		this.resetColor = PREFIX + "0" + POSTFIX;
	}

	@Override
	protected void keepLine(String line)
	{
		resetColors();
		actualLine.append(line);
		expectedLine.append(line);
	}

	@Override
	protected void insertLine(String line)
	{
		actualLine.append(paddingColor).append(Strings.repeat(paddingMarker, line.length()));
		expectedLine.append(insertColor).append(line);
		needToResetExpected = true;
	}

	@Override
	protected void deleteLine(String line)
	{
		actualLine.append(deleteColor).append(line);
		needToResetActual = true;
		expectedLine.append(paddingColor).append(Strings.repeat(paddingMarker, line.length()));
	}

	@Override
	protected void beforeClose()
	{
		resetColors();
	}

	@Override
	@SuppressWarnings("NoopMethodInAbstractClass")
	protected void afterClose()
	{
	}

	/**
	 * Resets the colors of {@code Expected} and {@code Actual}.
	 */
	private void resetColors()
	{
		if (needToResetActual)
		{
			actualLine.append(resetColor);
			needToResetActual = false;
		}
		if (needToResetExpected)
		{
			expectedLine.append(resetColor);
			needToResetExpected = false;
		}
	}

	@Override
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getMiddle()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return Collections.emptyList();
	}
}
