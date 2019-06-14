/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.diff;

import org.bitbucket.cowwoc.requirements.java.internal.util.Lists;

import java.util.List;

/**
 * The result of calculating the difference between two strings.
 */
public final class DiffResult
{
	private final List<String> actualLines;
	private final List<String> middleLines;
	private final List<String> expectedLines;
	private final String paddingMarker;

	/**
	 * @param actualLines   the lines of the actual string
	 * @param middleLines   the optional lines to display between "actual" and "expected"
	 * @param expectedLines the lines of the expected string
	 * @param paddingMarker a padding character used to align values vertically
	 * @throws NullPointerException     if any of the arguments are null
	 * @throws IllegalArgumentException if {@code paddingMarker} is empty
	 */
	public DiffResult(List<String> actualLines, List<String> middleLines, List<String> expectedLines,
	                  String paddingMarker)
	{
		if (actualLines == null)
			throw new NullPointerException("actualLines may not be null");
		if (middleLines == null)
			throw new NullPointerException("middleLines may not be null");
		if (expectedLines == null)
			throw new NullPointerException("expectedLines may not be null");
		if (paddingMarker == null)
			throw new NullPointerException("paddingMarker may not be null");
		if (paddingMarker.isEmpty())
			throw new IllegalArgumentException("paddingMarker may not be empty");
		this.actualLines = Lists.unmodifiable(actualLines);
		this.middleLines = Lists.unmodifiable(middleLines);
		this.expectedLines = Lists.unmodifiable(expectedLines);
		this.paddingMarker = paddingMarker;
	}

	/**
	 * Returns the lines of the actual string.
	 *
	 * @return an unmodifiable list
	 */
	public List<String> getActualLines()
	{
		return actualLines;
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
	public List<String> getMiddleLines()
	{
		return middleLines;
	}

	/**
	 * Returns the lines of the expected string.
	 *
	 * @return an unmodifiable list
	 */
	public List<String> getExpectedLines()
	{
		return expectedLines;
	}

	@Override
	public String toString()
	{
		return "actual: " + actualLines + "\n" +
			"middle: " + middleLines + "\n" +
			"expected: " + expectedLines;
	}
}
