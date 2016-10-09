/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import java.util.List;
import org.bitbucket.cowwoc.requirements.util.Lists;

/**
 * The result of calculating the difference between two strings.
 *
 * @author Gili Tzabari
 */
public final class DiffResult
{
	private final List<String> actual;
	private final List<String> middle;
	private final List<String> expected;

	/**
	 * Creates a new instance.
	 *
	 * @param actual   the lines of the actual string
	 * @param middle   the optional lines to display between "actual" and "expected"
	 * @param expected the lines of the expected string
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult(List<String> actual, List<String> middle, List<String> expected)
		throws NullPointerException
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (middle == null)
			throw new NullPointerException("middle may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		this.actual = Lists.unmodifiable(actual);
		this.middle = Lists.unmodifiable(middle);
		this.expected = Lists.unmodifiable(expected);
	}

	/**
	 * Returns the lines of the actual string.
	 *
	 * @return an unmodifiable list
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getActual()
	{
		return actual;
	}

	/**
	 * Returns the lines to display between "actual" and "expected".
	 * If the list is empty, no lines should be displayed.
	 *
	 * @return an unmodifiable list
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getMiddle()
	{
		return middle;
	}

	/**
	 * Returns the lines of the expected string.
	 *
	 * @return an unmodifiable list
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getExpected()
	{
		return expected;
	}

	@Override
	public String toString()
	{
		return "actual: " + actual + "\n" +
			"middle: " + middle + "\n" +
			"expected: " + expected;
	}
}
