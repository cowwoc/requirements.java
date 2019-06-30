/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import org.bitbucket.cowwoc.requirements.java.internal.util.Strings;

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
 * <li>Space (&nbsp;) indicates characters that needs to be deleted from Actual.</li>
 * <li>Equal sign ({@code =}) indicates characters that are equal in Actual and Expected.</li>
 * <li>Up arrowhead ({@code ^}) indicates characters that needs to be inserted into Actual.</li>
 * <li>"Diff" is omitted for lines that are identical.</li>
 * <li>Characters in the opposite direction of '&nbsp;' or '{@code ^}' are padded to line up the
 * strings vertically. This padding does not contribute any characters to the string it is found in.
 * Read on for concrete examples.</li>
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
 * Actual@1  : first
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
 * Expected@5: fifth\0
 * }
 * </pre>
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
 * <pre>{@code
 *
 * Actual@1  : Foo\n
 * Diff      :
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
	public static final String PADDING_MARKER = " ";
	private final StringBuilder middleLineBuilder = new StringBuilder(LINE_LENGTH);
	private final List<String> middleLinesBuilder = new ArrayList<>();
	private List<String> middleLines;

	public TextOnly()
	{
		super(PADDING_MARKER);
	}

	@Override
	public void writeUnchanged(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(text);
		middleLineBuilder.append(Strings.repeat(DIFF_EQUAL, text.length()));
		expectedLineBuilder.append(text);
	}

	@Override
	public void writeInserted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		int length = text.length();
		actualLineBuilder.append(Strings.repeat(getPaddingMarker(), length));
		middleLineBuilder.append(Strings.repeat(DIFF_INSERT, length));
		expectedLineBuilder.append(text);
	}

	@Override
	public void writeDeleted(String text)
	{
		if (closed)
			throw new IllegalStateException("Writer must be open");
		actualLineBuilder.append(text);
		int length = text.length();
		middleLineBuilder.append(Strings.repeat(DIFF_DELETE, length));
		expectedLineBuilder.append(Strings.repeat(getPaddingMarker(), length));
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