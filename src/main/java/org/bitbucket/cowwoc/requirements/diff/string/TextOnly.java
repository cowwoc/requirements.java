/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.base.Strings;
import java.util.Optional;

/**
 * A diff representation that does not use ANSI escape codes.
 * <h3>Example 1</h3>
 * <pre>
 * Actual   = "foosball"
 * Expected = "ballroom"
 * </pre>
 * results in the following diff:
 * <pre>
 * Actual  : foosball
 * Diff    : ^^^^====vvvv
 * Expected:     ballroom
 * </pre>
 * Vertical arrows ('{@code ^}' and '{@code v}') point in the direction of the text that needs to
 * be inserted or deleted into {@code Actual}. Arrows pointing upward indicate text that needs to
 * be deleted. Arrows pointing downward indicate text that needs to be inserted.
 * <p>
 * The back of the arrows contain padding in order to line up the strings vertically. This padding
 * does not contribute any characters to the string it is found in (meaning, there are no
 * characters in front of "ballroom"). Finally, the diff character '{@code =}' indicates that a
 * section of text that is equal.
 * <p>
 * In the above example, to go from {@code Actual} to {@code Expected} we need to delete "foos" and
 * insert "room".
 * <h3>Example 2</h3>
 * <pre>
 * Actual   = "Foo"
 * Expected = "   Foo"
 * </pre>
 * results in the following diff:
 * <pre>
 * Actual  :    Foo
 * Diff    : vvv===
 * Expected:    Foo
 * </pre>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to insert three spaces at the
 * beginning of {@code Actual}.
 * <h3>Example 3</h3>
 * <pre>
 * Actual   = ""
 * Expected = "text"
 * </pre>
 * results in the following diff:
 * <pre>
 * Actual  :
 * Diff    : vvvv
 * Expected: text
 * </pre>
 * Meaning, to go from {@code Actual} to {@code Expected} we need to insert "text".
 *
 * @author Gili Tzabari
 */
final class TextOnly implements DiffWriter
{
	private final StringBuilder actual;
	private final StringBuilder middle;
	private final StringBuilder expected;

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
		this.actual = new StringBuilder((int) (actual.length() * 1.1));
		this.middle = new StringBuilder(actual.length());
		this.expected = new StringBuilder((int) (expected.length() * 1.1));
	}

	@Override
	public void unchanged(String text)
	{
		int length = text.length();
		actual.append(text);
		middle.append(Strings.repeat("=", length));
		expected.append(text);
	}

	@Override
	public void inserted(String text)
	{
		int length = text.length();
		actual.append(Strings.repeat(" ", length));
		middle.append(Strings.repeat("v", length));
		expected.append(text);
	}

	@Override
	public void deleted(String text)
	{
		int length = text.length();
		actual.append(text);
		middle.append(Strings.repeat("^", length));
		expected.append(Strings.repeat(" ", length));
	}

	@Override
	public void close()
	{
		actual.append("\n").append(middle);
	}

	@Override
	public String getActual()
	{
		return actual.toString();
	}

	@Override
	public String getExpected()
	{
		return expected.toString();
	}

	@Override
	public Optional<String> getMiddle()
	{
		return Optional.of(middle.toString());
	}
}
