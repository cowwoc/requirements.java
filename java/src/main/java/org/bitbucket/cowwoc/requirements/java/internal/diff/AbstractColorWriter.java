/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import java.util.Collections;
import java.util.List;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.POSTFIX;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.PREFIX;

/**
 * Base implementation for all XTerm terminals.
 * <h3>Basic Rules</h3>
 * <ul>
 * <li>Red characters need to be deleted from {@code actual}.</li>
 * <li>Uncolored characters are equal in {@code actual} and {@code expected}.</li>
 * <li>Green characters need to be inserted into {@code actual}.</li>
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
 * Meaning, to go from {@code actual} to {@code expected} we need to insert "text".
 * <h3>Example 2: delete</h3>
 * <pre>{@code
 * Actual   = "text"
 * Expected = ""
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example2.png" alt="diff output">
 * <p>
 * Meaning, to go from {@code actual} to {@code expected} we need to delete "text".
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
 * <li>To go from {@code actual} to {@code expected} we need to insert three spaces at the beginning
 * of {@code actual}.</li>
 * <li>There are no {@code ///} characters in {@code expected} in front of "foo". This padding is
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
 * <h3>Example 5: Objects with the same toString() that are not equal</h3>
 * <ul>
 * <li>If objects are not equal, and their {@code toString()} values differ, we output their String
 * representations.</li>
 * <li>If the {@code toString()} values are equal, but their types differ, we output the string
 * representation of {@code actual} followed by the two types (i.e. {@code actual.getClass()} vs
 * {@code expected.getClass()}).</li>
 * <li>If their classes are equal, but their {@code hashCode()} values differ, we output the string
 * representation of {@code actual} followed by the two hashcodes (i.e. {@code actual.hashCode()} vs
 * {@code expected.hashCode()}).</li>
 * </ul>
 * For example:
 * <pre>{@code
 * Actual   = "null"
 * Expected = null
 * }</pre>
 * results in the following diff:
 * <p>
 * <img src="doc-files/xterm-example5.png" alt="diff output">
 * <h3>Multi-line Strings</h3>
 * When comparing multi-line strings:
 * <ul>
 * <li>We display the diff on a per-line basis.</li>
 * <li>{@code actual} and {@code expected} are followed by a line number.</li>
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
 * <img src="doc-files/xterm-example6.png" alt="diff output">
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
 * When {@code actual} or {@code expected} contain a line that does not have a corresponding line on
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
 * @see <a href="https://stackoverflow.com/a/33206814/14731">Summary of ANSI color sequences</a>
 */
abstract class AbstractColorWriter extends AbstractDiffWriter
	implements ColoredDiff
{
	/**
	 * A padding character used to align values vertically.
	 */
	protected static final String DIFF_PADDING = "/";
	private boolean needToResetActual;
	private boolean needToResetExpected;

	protected AbstractColorWriter()
	{
		super(DIFF_PADDING);
	}

	@Override
	public String stopDecoration()
	{
		return PREFIX + "0" + POSTFIX;
	}

	@Override
	public void writeUnchanged(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(decorateUnchangedText(text));
		expectedLineBuilder.append(decorateUnchangedText(text));
		needToResetActual = true;
		needToResetExpected = true;
	}

	@Override
	public void writeInserted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(decoratePadding(text.length()));
		expectedLineBuilder.append(decorateInsertedText(text));
		needToResetActual = true;
		needToResetExpected = true;
	}

	@Override
	public void writeDeleted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(decorateDeletedText(text));
		expectedLineBuilder.append(decoratePadding(text.length()));
		needToResetActual = true;
		needToResetExpected = true;
	}

	@Override
	protected void beforeNewline()
	{
		if (needToResetActual)
		{
			actualLineBuilder.append(stopDecoration());
			needToResetActual = false;
		}
		if (needToResetExpected)
		{
			expectedLineBuilder.append(stopDecoration());
			needToResetExpected = false;
		}
	}

	@Override
	protected void afterClose()
	{
	}

	@Override
	public List<String> getMiddleLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return Collections.emptyList();
	}
}
