/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import java.util.List;

/**
 * The result of calculating the difference between two strings.
 */
public final class DiffResult
{
	private final List<String> actualLines;
	private final List<String> diffLines;
	private final List<String> expectedLines;
	private final List<Boolean> equalLines;

	/**
	 * @param actualLines   the lines of the actual string
	 * @param diffLines     optional lines denoting the difference between "actual" and "expected"
	 * @param expectedLines the lines of the expected string
	 * @param equalLines    indicates which lines are equal
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult(List<String> actualLines, List<String> diffLines, List<String> expectedLines,
		List<Boolean> equalLines)
	{
		if (actualLines == null)
			throw new NullPointerException("actualLines may not be null");
		if (expectedLines == null)
			throw new NullPointerException("expectedLines may not be null");
		if (diffLines == null)
			throw new NullPointerException("diffLines may not be null");
		this.actualLines = List.copyOf(actualLines);
		this.diffLines = List.copyOf(diffLines);
		this.expectedLines = List.copyOf(expectedLines);
		this.equalLines = List.copyOf(equalLines);
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
	 * Returns the difference between "actual" and "expected".
	 * If the list is empty, no lines should be displayed.
	 *
	 * @return an unmodifiable list
	 */
	public List<String> getDiffLines()
	{
		return diffLines;
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

	/**
	 * Returns a list that indicates whether the actual and expected values are equal on each line.
	 *
	 * @return an unmodifiable list
	 */
	public List<Boolean> getEqualLines()
	{
		return equalLines;
	}

	@Override
	public String toString()
	{
		return "actual  : " + actualLines + "\n" +
			"diff    : " + diffLines + "\n" +
			"expected: " + expectedLines + "\n" +
			"equal   : " + equalLines;
	}
}