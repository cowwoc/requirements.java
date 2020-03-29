/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.internal.util.Lists;

import java.util.List;

/**
 * The result of calculating the difference between two strings.
 */
public final class DiffResult
{
	private final List<String> actualLines;
	private final List<String> diffLines;
	private final List<String> expectedLines;

	/**
	 * @param actualLines   the lines of the actual string
	 * @param diffLines     optional lines denoting the difference between "actual" and "expected"
	 * @param expectedLines the lines of the expected string
	 * @throws NullPointerException if any of the arguments are null
	 */
	public DiffResult(List<String> actualLines, List<String> diffLines, List<String> expectedLines)
	{
		if (actualLines == null)
			throw new NullPointerException("actualLines may not be null");
		if (expectedLines == null)
			throw new NullPointerException("expectedLines may not be null");
		if (diffLines == null)
			throw new NullPointerException("diffLines may not be null");
		this.actualLines = Lists.unmodifiable(actualLines);
		this.diffLines = Lists.unmodifiable(diffLines);
		this.expectedLines = Lists.unmodifiable(expectedLines);
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

	@Override
	public String toString()
	{
		return "actual  : " + actualLines + "\n" +
			"diff    : " + diffLines + "\n" +
			"expected: " + expectedLines;
	}
}
