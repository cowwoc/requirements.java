/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.base.Strings;
import java.util.Optional;

/**
 * Base implementation for all XTerm terminals.
 *
 * @author Gili Tzabari
 */
abstract class AbstractXterm implements ColoredDiff
{
	/**
	 * The prefix for all ANSI sequences.
	 */
	protected static final String PREFIX = "\033[";
	/**
	 * The postfix for all color sequences.
	 */
	protected static final String POSTFIX = "m";
	private final StringBuilder actual;
	private final StringBuilder expected;
	private final String colorForInsert;
	private final String colorForDelete;
	private final String colorForNeutral;
	private final String resetColor;
	private boolean needToResetColor;

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
		this.actual = new StringBuilder((int) (actual.length() * 1.1));;
		this.expected = new StringBuilder((int) (expected.length() * 1.1));
		this.colorForNeutral = getColorForNeutral();
		this.colorForInsert = getColorForInsert();
		this.colorForDelete = getColorForDelete();
		this.resetColor = PREFIX + "0" + POSTFIX;
	}

	@Override
	public void unchanged(String text)
	{
		actual.append(resetColor).append(text);
		expected.append(resetColor).append(text);
		needToResetColor = false;
	}

	@Override
	public void inserted(String text)
	{
		actual.append(colorForNeutral).append(Strings.repeat("/", text.length()));
		expected.append(colorForInsert).append(text);
		needToResetColor = true;
	}

	@Override
	public void deleted(String text)
	{
		actual.append(colorForDelete).append(text);
		expected.append(colorForNeutral).append(Strings.repeat("/", text.length()));
		needToResetColor = true;
	}

	@Override
	public void close()
	{
		if (needToResetColor)
		{
			actual.append(resetColor);
			expected.append(resetColor);
		}
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
		return Optional.empty();
	}
}
