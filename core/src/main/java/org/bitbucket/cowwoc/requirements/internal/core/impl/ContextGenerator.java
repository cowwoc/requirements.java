/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.internal.core.diff.DiffResult;
import org.bitbucket.cowwoc.requirements.internal.core.util.Strings;

/**
 * The values to compare.
 */
public class ContextGenerator
{
	private static final Pattern LINES_NOT_EQUAL = Pattern.compile("[^=]+");

	/**
	 * Updates the last context entry to indicate that duplicate lines were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipDuplicateLines(List<Map.Entry<String, Object>> entries)
	{
		Entry<String, Object> lastEntry = entries.get(entries.size() - 1);
		String newValue = lastEntry.getValue() + "\n[...]\n";
		entries.set(entries.size() - 1, new SimpleImmutableEntry<>(lastEntry.getKey(), newValue));
	}

	/**
	 * @param diff the textual diff of two lines
	 * @return true if the lines being compared are equal to each other
	 */
	private static boolean linesAreEqual(String diff)
	{
		return !LINES_NOT_EQUAL.matcher(diff).find();
	}
	private final Configuration config;
	private final DiffGenerator diffGenerator;

	/**
	 * @param configuration the instance configuration
	 * @param diffGenerator an instance of {@link DiffGenerator}
	 * @throws AssertionError if {@code configuration}, {@code diffGenerator} are null
	 */
	ContextGenerator(Configuration configuration, DiffGenerator diffGenerator)
	{
		assert (configuration != null): "configuration may not be null";
		assert (diffGenerator != null): "diffGenerator may not be null";
		this.config = configuration;
		this.diffGenerator = diffGenerator;
	}

	/**
	 * @param actualName    the name of the actual value
	 * @param actualValue   the actual value
	 * @param expectedName  the name of the expected value
	 * @param expectedValue the expected value
	 * @return the list of name-value pairs to append to the exception message
	 */
	public List<Entry<String, Object>> getContext(String actualName, Object actualValue,
		String expectedName, Object expectedValue)
	{
		String actualToString = config.toString(actualValue);
		String expectedToString = config.toString(expectedValue);
		Class<?> actualType;
		if (actualValue == null)
			actualType = null;
		else
			actualType = actualValue.getClass();
		List<Entry<String, Object>> result = new ArrayList<>(3);
		result.addAll(getContext(actualName, actualToString, actualType, expectedName,
			expectedToString));
		if (actualToString.equals(expectedToString))
		{
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
			String actualHashCode = config.toString(Objects.hashCode(actualValue));
			String expectedHashCode = config.toString(Objects.hashCode(expectedValue));

			result.add(null);
			result.addAll(getContext(actualName + ".class", actualClassName, actualType,
				expectedName + ".class", expectedClassName));
			result.add(null);
			result.addAll(getContext(actualName + ".hashCode", actualHashCode, actualType,
				expectedName + ".hashCode", expectedHashCode));
		}
		return result;
	}

	/**
	 * @param actualName    the name of the actual value
	 * @param actualValue   the actual value
	 * @param actualType    the type of the actual value
	 * @param expectedName  the name of the expected value
	 * @param expectedValue the expected value
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<Entry<String, Object>> getContext(String actualName, String actualValue,
		Class<?> actualType, String expectedName, String expectedValue)
	{
		assert (actualName != null): "actualName may not be null";
		assert (expectedName != null): "expectedName may not be null";
		boolean typeIsDiffable = (actualType != boolean.class) && (actualType != Boolean.class);
		if (!typeIsDiffable || !config.isDiffEnabled())
		{
			List<Entry<String, Object>> result = new ArrayList<>(2);
			result.add(new SimpleImmutableEntry<>(actualName, actualValue));
			result.add(new SimpleImmutableEntry<>(expectedName, expectedValue));
			return result;
		}
		DiffResult diff = diffGenerator.diff(actualValue, expectedValue);
		List<String> actualDiffs = diff.getActual();
		List<String> middleDiffs = diff.getMiddle();
		List<String> expectedDiffs = diff.getExpected();
		int lines = actualDiffs.size();
		List<Map.Entry<String, Object>> result = new ArrayList<>(2 * lines);
		if (lines == 1)
		{
			result.add(new SimpleImmutableEntry<>(actualName, actualDiffs.get(0)));
			if (!middleDiffs.isEmpty() && !linesAreEqual(middleDiffs.get(0)))
				result.add(new SimpleImmutableEntry<>("Diff", middleDiffs.get(0)));
			result.add(new SimpleImmutableEntry<>(expectedName, expectedDiffs.get(0)));
			return result;
		}
		assert (expectedDiffs.size() == lines): "lines: " + lines + ", expected.size(): " +
			expectedDiffs.size();
		int actualLineNumber = 1;
		int expectedLineNumber = 1;
		// Indicates if the previous line was identical
		boolean skippedDuplicates = false;
		for (int i = 0; i < lines; ++i)
		{
			String actualLine = actualDiffs.get(i);
			String expectedLine = expectedDiffs.get(i);
			if (i != 0 && i != lines - 1 && actualLine.equals(expectedLine))
			{
				// Skip identical lines, unless they are the first or last line.
				skippedDuplicates = true;
				++actualLineNumber;
				++expectedLineNumber;
				continue;
			}
			String actualNameForLine;
			if (Strings.containsOnly(actualLine, diff.getPaddingMarker()))
				actualNameForLine = actualName;
			else
			{
				actualNameForLine = actualName + "@" + actualLineNumber;
				++actualLineNumber;
			}
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateLines(result);
			}
			result.add(new SimpleImmutableEntry<>(actualNameForLine, actualLine));
			if (!middleDiffs.isEmpty() && !linesAreEqual(middleDiffs.get(i)))
				result.add(new SimpleImmutableEntry<>("Diff", middleDiffs.get(i)));
			String expectedNameForLine;
			if (Strings.containsOnly(expectedLine, diff.getPaddingMarker()))
				expectedNameForLine = expectedName;
			else
			{
				expectedNameForLine = expectedName + "@" + expectedLineNumber;
				++expectedLineNumber;
			}
			if (i < lines - 1)
				expectedLine += "\n";
			result.add(new SimpleImmutableEntry<>(expectedNameForLine, expectedLine));
		}
		if (skippedDuplicates)
			skipDuplicateLines(result);
		return result;
	}
}
