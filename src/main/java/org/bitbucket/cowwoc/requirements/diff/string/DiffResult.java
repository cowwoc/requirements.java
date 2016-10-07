/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.diff.string;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import java.util.List;

/**
 * The result of calculating the difference between two strings.
 *
 * @author Gili Tzabari
 */
@Beta
public final class DiffResult
{
	private final ImmutableList<String> actual;
	private final ImmutableList<String> middle;
	private final ImmutableList<String> expected;

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
		this.actual = ImmutableList.copyOf(actual);
		this.middle = ImmutableList.copyOf(middle);
		this.expected = ImmutableList.copyOf(expected);
	}

	/**
	 * @return the lines of the actual string
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getActual()
	{
		return actual;
	}

	/**
	 * @return the optional lines to display between "actual" and "expected"
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> getMiddle()
	{
		return middle;
	}

	/**
	 * @return the lines of the expected string
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
