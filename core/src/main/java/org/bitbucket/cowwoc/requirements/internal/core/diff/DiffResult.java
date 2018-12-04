/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.diff;

import org.bitbucket.cowwoc.requirements.internal.core.util.Lists;

import java.util.List;

/**
 * The result of calculating the difference between two strings.
 */
public final class DiffResult
{
	private final List<String> actual;
	private final List<String> middle;
	private final List<String> expected;
	private final String paddingMarker;

	/**
	 * Creates a new instance.
	 *
	 * @param actual        the lines of the actual string
	 * @param middle        the optional lines to display between "actual" and "expected"
	 * @param expected      the lines of the expected string
	 * @param paddingMarker a padding character used to align values vertically
	 * @throws NullPointerException     if any of the arguments are null
	 * @throws IllegalArgumentException if {@code paddingMarker} is empty
	 */
	public DiffResult(List<String> actual, List<String> middle, List<String> expected, String paddingMarker)
	{
		if (actual == null)
			throw new NullPointerException("actual may not be null");
		if (middle == null)
			throw new NullPointerException("middle may not be null");
		if (expected == null)
			throw new NullPointerException("expected may not be null");
		if (paddingMarker == null)
			throw new NullPointerException("paddingMarker may not be null");
		if (paddingMarker.isEmpty())
			throw new IllegalArgumentException("paddingMarker may not be empty");
		this.actual = Lists.unmodifiable(actual);
		this.middle = Lists.unmodifiable(middle);
		this.expected = Lists.unmodifiable(expected);
		this.paddingMarker = paddingMarker;
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
	 * @return a padding character used to align values vertically
	 */
	public String getPaddingMarker()
	{
		return paddingMarker;
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
