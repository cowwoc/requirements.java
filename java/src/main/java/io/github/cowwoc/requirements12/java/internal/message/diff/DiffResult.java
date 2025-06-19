/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.message.diff;

import java.util.Collection;
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
	 * @param diffLines     the difference between the actual and expected values (empty list if omitted)
	 * @param expectedLines the lines of the expected string
	 * @param equalLines    indicates if the actual and expected values are equal for each line
	 */
	public DiffResult(Collection<String> actualLines, Collection<String> diffLines,
		Collection<String> expectedLines, Collection<Boolean> equalLines)
	{
		if (actualLines == null)
			throw new NullPointerException("actualLines may not be null");
		if (diffLines == null)
			throw new NullPointerException("diffLines may not be null");
		if (expectedLines == null)
			throw new NullPointerException("expectedLines may not be null");
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
	 * Returns the difference between "actual" and "expected". If the list is empty, no lines should be
	 * displayed.
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