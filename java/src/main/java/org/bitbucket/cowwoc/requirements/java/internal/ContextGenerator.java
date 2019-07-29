/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffResult;
import org.bitbucket.cowwoc.requirements.java.internal.util.Strings;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.EOL_PATTERN;

/**
 * Returns the difference between two values as an exception context.
 */
public final class ContextGenerator
{
	private static final Pattern LINES_NOT_EQUAL = Pattern.compile("[^" + DIFF_EQUAL + "]+");

	/**
	 * Updates the last context entry to indicate that duplicate lines were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipDuplicateLines(List<Entry<String, Object>> entries)
	{
		Entry<String, Object> lastEntry = entries.get(entries.size() - 1);
		String newValue = lastEntry.getValue() + "\n[...]\n";
		entries.set(entries.size() - 1, new SimpleImmutableEntry<>(lastEntry.getKey(), newValue));
	}

	/**
	 * @param actualLines   the actual lines
	 * @param middleLines   the middle lines
	 * @param expectedLines the expected lines
	 * @param line          the current line number (0-based)
	 * @return true if the lines being compared are equal to each other
	 */
	private static boolean linesAreEqual(List<String> actualLines, List<String> middleLines,
	                                     List<String> expectedLines, int line)
	{
		if (!middleLines.isEmpty())
			return !LINES_NOT_EQUAL.matcher(middleLines.get(line)).find();
		return actualLines.get(line).equals(expectedLines.get(line));
	}

	private final Configuration config;
	private final DiffGenerator diffGenerator;

	/**
	 * @param configuration the instance configuration
	 * @param diffGenerator an instance of {@link DiffGenerator}
	 * @throws AssertionError if {@code configuration}, {@code diffGenerator} are null
	 */
	public ContextGenerator(Configuration configuration, DiffGenerator diffGenerator)
	{
		assert (configuration != null) : "configuration may not be null";
		assert (diffGenerator != null) : "diffGenerator may not be null";
		this.config = configuration;
		this.diffGenerator = diffGenerator;
	}

	/**
	 * @param actualName        the name of the actual value
	 * @param actualValue       the actual value
	 * @param expectedName      the name of the expected value
	 * @param expectedValue     the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	public List<Entry<String, Object>> getContext(String actualName, Object actualValue, String expectedName,
	                                              Object expectedValue, boolean expectedInMessage)
	{
		// This class outputs the String representation of the values. If those are equal, it also
		// outputs the first of getClass(), hashCode(), or System.identityHashCode()] that differs.
		String actualAsString = config.toString(actualValue);
		String expectedAsString = config.toString(expectedValue);
		Class<?> actualType;
		if (actualValue == null)
			actualType = null;
		else
			actualType = actualValue.getClass();
		List<Entry<String, Object>> result = new ArrayList<>(3);
		result.addAll(getContext(actualName, actualAsString, actualType, expectedName, expectedAsString,
			expectedInMessage));
		if (actualAsString.equals(expectedAsString))
		{
			result.add(null);

			String actualClassName;
			if (actualType == null)
				actualClassName = "null";
			else
				actualClassName = actualType.getName();
			String expectedClassName;
			if (expectedValue == null)
				expectedClassName = "null";
			else
				expectedClassName = expectedValue.getClass().getName();
			if (!actualClassName.equals(expectedClassName))
			{
				result.addAll(getContext(actualName + ".class", actualClassName, actualType,
					expectedName + ".class", expectedClassName, false));
				return result;
			}
			String actualHashCode = config.toString(Objects.hashCode(actualValue));
			String expectedHashCode = config.toString(Objects.hashCode(expectedValue));
			if (!actualHashCode.equals(expectedHashCode))
			{
				result.addAll(getContext(actualName + ".hashCode", actualHashCode, actualType,
					expectedName + ".hashCode", expectedHashCode, false));
				return result;
			}
			String actualIdentityHashCode = config.toString(System.identityHashCode(actualValue));
			String expectedIdentityHashCode = config.toString(System.identityHashCode(expectedValue));
			result.addAll(getContext(actualName + ".identityHashCode", actualIdentityHashCode,
				actualType, expectedName + ".identityHashCode", expectedIdentityHashCode, false));
		}
		return result;
	}

	/**
	 * @param actualName        the name of the actual value
	 * @param actualValue       the actual value
	 * @param actualType        the type of the actual value
	 * @param expectedName      the name of the expected value
	 * @param expectedValue     the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<Entry<String, Object>> getContext(String actualName, String actualValue, Class<?> actualType,
	                                               String expectedName, String expectedValue,
	                                               boolean expectedInMessage)
	{
		assert (actualName != null) : "actualName may not be null";
		// actualType is null if actualValue is null
		assert (expectedName != null) : "expectedName may not be null";
		// Don't diff booleans
		boolean typeIsDiffable = (actualType != boolean.class) && (actualType != Boolean.class);
		if (!typeIsDiffable || !config.isDiffEnabled())
		{
			List<Entry<String, Object>> result = new ArrayList<>(2);
			result.add(new SimpleImmutableEntry<>(actualName, actualValue));
			if (!expectedInMessage)
				result.add(new SimpleImmutableEntry<>(expectedName, expectedValue));
			return result;
		}
		DiffResult diff = diffGenerator.diff(actualValue, expectedValue);
		List<String> actualLines = diff.getActualLines();
		List<String> middleLines = diff.getMiddleLines();
		List<String> expectedLines = diff.getExpectedLines();
		int lines = actualLines.size();
		List<Map.Entry<String, Object>> result = new ArrayList<>(2 * lines);
		if (lines == 1)
		{
			result.add(new SimpleImmutableEntry<>(actualName, actualLines.get(0)));
			if (!middleLines.isEmpty() && !linesAreEqual(actualLines, middleLines, expectedLines, 0))
				result.add(new SimpleImmutableEntry<>("Diff", middleLines.get(0)));
			result.add(new SimpleImmutableEntry<>(expectedName, expectedLines.get(0)));
			return result;
		}
		assert (expectedLines.size() == lines) : "lines: " + lines + ", expected.size(): " +
			expectedLines.size();
		int actualLineNumber = 1;
		int expectedLineNumber = 1;
		// Indicates if the previous line was identical
		boolean skippedDuplicates = false;
		for (int i = 0; i < lines; ++i)
		{
			String actualLine = actualLines.get(i);
			String expectedLine = expectedLines.get(i);
			boolean linesAreEqual = linesAreEqual(actualLines, middleLines, expectedLines, i);
			if (i != 0 && i != lines - 1 && linesAreEqual)
			{
				// Skip identical lines, unless they are the first or last line.
				skippedDuplicates = true;
				++actualLineNumber;
				++expectedLineNumber;
				continue;
			}
			String actualNameForLine;
			if (!Strings.containsOnly(actualLine, diff.getPaddingMarker()))
			{
				actualNameForLine = actualName + "@" + actualLineNumber;
				if (EOL_PATTERN.matcher(actualLine).find())
					++actualLineNumber;
			}
			else
				actualNameForLine = actualName;
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateLines(result);
			}
			result.add(new SimpleImmutableEntry<>(actualNameForLine, actualLine));
			if (!middleLines.isEmpty() && !linesAreEqual)
				result.add(new SimpleImmutableEntry<>("Diff", middleLines.get(i)));
			String expectedNameForLine;
			if (!Strings.containsOnly(expectedLine, diff.getPaddingMarker()))
			{
				expectedNameForLine = expectedName + "@" + expectedLineNumber;
				if (EOL_PATTERN.matcher(expectedLine).find())
					++expectedLineNumber;
			}
			else
				expectedNameForLine = expectedName;
			if (i < lines - 1)
				expectedLine += "\n";
			result.add(new SimpleImmutableEntry<>(expectedNameForLine, expectedLine));
		}
		if (skippedDuplicates)
			skipDuplicateLines(result);
		return result;
	}
}
