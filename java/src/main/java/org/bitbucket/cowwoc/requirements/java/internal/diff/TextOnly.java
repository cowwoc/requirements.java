/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_DELETE;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_INSERT;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.LINE_LENGTH;

/**
 * A diff representation that does not use ANSI escape codes.
 * <h2>Basic Rules</h2>
 * <ul>
 * <li>Minus ({@code -}) denotes a character that needs to be removed from {@code Actual}.</li>
 * <li>Space ( ) denotes a character that is equal in {@code Actual} and {@code
 * Expected}.</li>
 * <li>Plus ({@code +}) denotes a character that needs to be added to {@code Actual}.</li>
 * <li>"Diff" is omitted for lines that are identical.</li>
 * <li>When '{@code -}' is present, {@code Actual} is padded to line up vertically with {@code Expected}.</li>
 * <li>When '{@code +}' is present, {@code Expected} is padded to line up vertically with {@code Actual}.</li>
 * <li>The padding is not part of {@code Actual} and {@code Expected}'s value, respectively. Read
 * on for concrete examples.
 * <li>Lines always end with {@code \n} or {@code \0}. The former denotes a newline. The latter denotes
 * the end of the string.</li>
 * <li>Lines ending with "\n\n" or "\0\0" represents the literal string "\n" followed by a newline
 * character, or the literal string "\0" followed by the end of string, respectively.</li>
 * </ul>
 * <h3>Example 1: insert</h3>
 * <pre>{@code
 * Actual   = ""
 * Expected = "text"
 * }</pre>results in the following diff:
 * <pre>{@code
 *
 * Actual  :     \0
 * Diff    : ++++
 * Expected: text\0
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
 * Actual  : text\0
 * Diff    : ----
 * Expected:     \0
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
 * Actual  :    foo\0
 * Diff    : +++
 * Expected:    foo\0
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
 * Actual  : foosball    \0
 * Diff    : ----    ++++
 * Expected:     ballroom\0
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
 * <h3>Example 5: Objects with the same toString() that are not equal</h3>
 * <ul>
 * <li>If objects are not equal, and their {@code toString()} values differ, we output their String
 * representations.</li>
 * <li>If the {@code toString()} values are equal, but their types differ, we output the string representation
 * of {@code Actual} followed by the two types (i.e. {@code Actual.class} vs {@code Expected.class}).</li>
 * <li>If their classes are equal, but their {@code hashCode()} values differ, we output the string
 * representation of {@code Actual} followed by the two hashcodes (i.e. {@code Actual.hashCode()} vs
 * {@code Expected.hashCode()}).</li>
 * </ul>
 * For example:
 * <pre>{@code
 * Actual   = "null"
 * Expected = null
 * }</pre>
 * results in the following diff:
 * <pre>{@code
 *
 * Actual        : null
 * Actual.class  : java.lang.String    \0
 * Diff          : ----------------++++
 * Expected.class:                 null\0
 * }
 * </pre>
 * <h3>Example 6: Multi-line Strings</h3>
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
 * <pre>{@code
 *
 * Actual@1  : first\n
 * Expected@1: first\n
 *
 * [...]
 *
 * Actual@3  : foo   \n
 * Diff      : ---+++
 * Expected@3:    bar\n
 *
 * [...]
 *
 * Actual@5  : fifth\0
 * Expected@5: fifth\0
 * }
 * </pre>
 * Meaning:
 * <ul>
 * <li>Lines 1-2 were equal.</li>
 * <li>On line 3, we need to delete "foo" and insert "bar".</li>
 * <li>Lines 4-5 were equal.</li>
 * </ul>
 * <h3>Example 7: Missing Line Numbers</h3>
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
 * Diff      : -----
 * Expected  :
 *
 * Actual@2  : Bar\0
 * Expected@1: Bar\0
 * }
 * </pre>
 * Meaning:
 * <ul>
 * <li>Actual contained more lines than Expected.</li>
 * <li>Expected did not have a line that corresponded to Actual line 1.</li>
 * <li>We need to delete line 1 and retain line 2 unchanged.</li>
 * </ul>
 */
public final class TextOnly extends AbstractDiffWriter
{
	/**
	 * A padding character used to align values vertically.
	 */
	public static final String DIFF_PADDING = " ";
	private final StringBuilder middleLineBuilder = new StringBuilder(LINE_LENGTH);
	private final List<String> middleLinesBuilder = new ArrayList<>();
	private List<String> middleLines;

	public TextOnly()
	{
		super(DIFF_PADDING);
	}

	@Override
	public void writeUnchanged(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(text);
		middleLineBuilder.append(DIFF_EQUAL.repeat(text.length()));
		expectedLineBuilder.append(text);
	}

	@Override
	public void writeInserted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		int length = text.length();
		actualLineBuilder.append(getPaddingMarker().repeat(length));
		middleLineBuilder.append(DIFF_INSERT.repeat(length));
		expectedLineBuilder.append(text);
	}

	@Override
	public void writeDeleted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(text);
		int length = text.length();
		middleLineBuilder.append(DIFF_DELETE.repeat(length));
		expectedLineBuilder.append(getPaddingMarker().repeat(length));
	}

	@Override
	protected void beforeNewline()
	{
	}

	@Override
	public void writeNewline()
	{
		super.writeNewline();
		middleLinesBuilder.add(middleLineBuilder.toString());
		middleLineBuilder.delete(0, middleLineBuilder.length());
	}

	@Override
	public String decoratePadding(int length)
	{
		return DIFF_PADDING.repeat(length);
	}

	@Override
	protected void afterClose()
	{
		this.middleLines = Collections.unmodifiableList(middleLinesBuilder);
	}

	@Override
	public List<String> getMiddleLines()
	{
		if (!closed)
			throw new IllegalStateException("Writer must be closed");
		return middleLines;
	}
}