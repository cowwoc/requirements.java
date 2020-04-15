/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOL_PATTERN;

/**
 * Returns the difference between two values as an exception context.
 */
public final class ContextGenerator
{
	private static final Pattern LINES_NOT_EQUAL = Pattern.compile("[^" + DIFF_EQUAL + "]+");

	/**
	 * Append context entries to indicate that duplicate lines were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipDuplicateLines(List<ContextLine> entries)
	{
		entries.add(new ContextLine(""));
		entries.add(new ContextLine("[...]"));
	}

	/**
	 * @param actualLine   the current line of the actual value
	 * @param expectedLine the current line of the expected value
	 * @param diffLine     the diff associated with the line
	 * @return true if the lines being compared are equal to each other
	 */
	private static boolean linesAreEqual(String actualLine, String expectedLine, String diffLine)
	{
		if (!diffLine.isEmpty())
			return !LINES_NOT_EQUAL.matcher(diffLine).find();
		return actualLine.equals(expectedLine);
	}

	private final Configuration config;
	private final DiffGenerator diffGenerator;

	/**
	 * @param configuration the instance configuration
	 * @param scope         the application configuration
	 * @throws AssertionError if {@code configuration} or {@code scope} are null
	 */
	public ContextGenerator(Configuration configuration, ApplicationScope scope)
	{
		assert (configuration != null) : "configuration may not be null";
		assert (scope != null) : "scope may not be null";
		this.config = configuration;
		this.diffGenerator = new DiffGenerator(scope.getGlobalConfiguration().getTerminalEncoding());
	}

	/**
	 * @param <T>               the type of elements in the list
	 * @param actualName        the name of the actual value
	 * @param actualValue       the actual value
	 * @param expectedName      the name of the expected value
	 * @param expectedValue     the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	public <T> List<ContextLine> getContext(String actualName, List<T> actualValue,
	                                        String expectedName, List<T> expectedValue,
	                                        boolean expectedInMessage)
	{
		assert (actualName != null) : "actualName may not be null";
		assert (expectedName != null) : "expectedName may not be null";
		if (!config.isDiffEnabled())
		{
			List<ContextLine> result = new ArrayList<>(2);
			result.add(new ContextLine(actualName, actualValue));
			if (!expectedInMessage)
				result.add(new ContextLine(expectedName, expectedValue));
			return result;
		}
		int actualSize = actualValue.size();
		int expectedSize = expectedValue.size();
		int maxSize = Math.max(actualSize, expectedSize);

		List<ContextLine> result = new ArrayList<>();
		// Indicates if the previous index was equal
		boolean skippedDuplicates = false;
		int actualIndex = 0;
		int expectedIndex = 0;
		for (int i = 0; i < maxSize; ++i)
		{
			boolean elementsAreEqual = true;
			String actualValueAsString;
			String actualNameForElement;
			if (i < actualSize)
			{
				actualValueAsString = config.toString(actualValue.get(i));
				actualNameForElement = actualName + "[" + actualIndex + "]";
				++actualIndex;
			}
			else
			{
				actualValueAsString = "";
				actualNameForElement = actualName;
				elementsAreEqual = false;
			}
			String expectedValueAsString;
			String expectedNameForElement;
			if (i < expectedSize)
			{
				expectedValueAsString = config.toString(expectedValue.get(i));
				expectedNameForElement = expectedName + "[" + expectedIndex + "]";
				++expectedIndex;
			}
			else
			{
				expectedValueAsString = "";
				expectedNameForElement = expectedName;
				elementsAreEqual = false;
			}
			if (elementsAreEqual)
				elementsAreEqual = actualValue.get(i).equals(expectedValue.get(i));
			if (i != 0 && i != maxSize - 1 && elementsAreEqual)
			{
				// Skip identical elements, unless they are the first or last element.
				skippedDuplicates = true;
				continue;
			}
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateLines(result);
			}
			result.addAll(getContext(actualNameForElement, actualValueAsString, expectedNameForElement,
				expectedValueAsString, false, false));
		}
		return result;
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
	public List<ContextLine> getContext(String actualName, Object actualValue,
	                                    String expectedName, Object expectedValue,
	                                    boolean expectedInMessage)
	{
		return getContext(actualName, actualValue, expectedName, expectedValue, expectedInMessage, true);
	}

	/**
	 * @param actualName        the name of the actual value
	 * @param actualValue       the actual value
	 * @param expectedName      the name of the expected value
	 * @param expectedValue     the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @param compareTypes      true if the actual and expected types should be compared if their values are
	 *                          equal
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<ContextLine> getContext(String actualName, Object actualValue,
	                                     String expectedName, Object expectedValue,
	                                     boolean expectedInMessage, boolean compareTypes)
	{
		// Don't diff booleans
		Class<?> actualType = getClass(actualValue);
		boolean typeIsDiffable = (actualType != boolean.class) && (actualType != Boolean.class);
		if (!typeIsDiffable || !config.isDiffEnabled())
		{
			List<ContextLine> result = new ArrayList<>(2);
			result.add(new ContextLine(actualName, actualValue));
			if (!expectedInMessage)
				result.add(new ContextLine(expectedName, expectedValue));
			return result;
		}
		String actualAsString = config.toString(actualValue);
		String expectedAsString = config.toString(expectedValue);
		DiffResult lines = diffGenerator.diff(actualAsString, expectedAsString);
		int numberOfLines = lines.getActualLines().size();
		List<ContextLine> result = new ArrayList<>(2 * numberOfLines);
		if (numberOfLines == 1)
		{
			String actualLine = lines.getActualLines().get(0);
			String expectedLine = lines.getExpectedLines().get(0);
			String diffLine;
			if (lines.getDiffLines().isEmpty())
				diffLine = "";
			else
				diffLine = lines.getDiffLines().get(0);
			boolean stringsAreEqual = linesAreEqual(actualLine, expectedLine, diffLine);
			result.add(new ContextLine(""));
			result.add(new ContextLine(actualName, actualLine));
			if (!diffLine.isEmpty() && !stringsAreEqual)
				result.add(new ContextLine("Diff", diffLine));
			result.add(new ContextLine(expectedName, expectedLine));

			if (compareTypes && linesAreEqual(actualLine, expectedLine, diffLine))
			{
				// If the String representation of the values is equal, output getClass(), hashCode(),
				// or System.identityHashCode()] that differ.
				result.addAll(compareTypes(actualName, actualValue, expectedName, expectedValue));
			}
			return result;
		}
		List<String> actualLines = lines.getActualLines();
		int actualLineNumber = 0;
		int expectedLineNumber = 0;
		// Indicates if the previous line was equal
		boolean skippedDuplicates = false;
		for (int i = 0; i < numberOfLines; ++i)
		{
			List<String> expectedLines = lines.getExpectedLines();
			String actualLine = actualLines.get(i);
			String expectedLine;
			if (expectedLines.size() > i)
				expectedLine = expectedLines.get(i);
			else
				expectedLine = "";
			String diffLine;
			if (lines.getDiffLines().isEmpty())
				diffLine = "";
			else
				diffLine = lines.getDiffLines().get(i);
			boolean linesAreEqual = linesAreEqual(actualLine, expectedLine, diffLine);
			if (i != 0 && i != numberOfLines - 1 && linesAreEqual)
			{
				// Skip identical lines, unless they are the first or last line.
				skippedDuplicates = true;
				++actualLineNumber;
				++expectedLineNumber;
				continue;
			}
			String actualNameForLine;
			if (diffGenerator.isEmpty(actualLine))
				actualNameForLine = actualName;
			else
			{
				actualNameForLine = actualName + "@" + actualLineNumber;
				if (EOL_PATTERN.matcher(actualLine).find())
					++actualLineNumber;
			}
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateLines(result);
			}
			result.add(new ContextLine(""));
			result.add(new ContextLine(actualNameForLine, actualLine));
			if (!diffLine.isEmpty() && !linesAreEqual)
				result.add(new ContextLine("Diff", diffLine));

			String expectedNameForLine;
			if (diffGenerator.isEmpty(expectedLine))
				expectedNameForLine = expectedName;
			else
			{
				expectedNameForLine = expectedName + "@" + expectedLineNumber;
				if (EOL_PATTERN.matcher(expectedLine).find())
					++expectedLineNumber;
			}
			result.add(new ContextLine(expectedNameForLine, expectedLine));
		}
		return result;
	}

	/**
	 * @param value a value
	 * @return the {@code Class} of a value or {@code null} if {@code value} is null
	 */
	private static Class<?> getClass(Object value)
	{
		if (value == null)
			return null;
		return value.getClass();
	}

	/**
	 * @param type a class
	 * @return the {@code String} representation of {@code type}
	 */
	private static String getClassName(Class<?> type)
	{
		if (type == null)
			return "null";
		return type.getName();
	}

	/**
	 * @param actualName    the name of the actual value
	 * @param actualValue   the actual value
	 * @param expectedName  the name of the expected value
	 * @param expectedValue the expected value
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<ContextLine> compareTypes(String actualName, Object actualValue, String expectedName,
	                                       Object expectedValue)
	{
		List<ContextLine> result = new ArrayList<>();
		Class<?> actualType = getClass(actualValue);
		String actualClassName = getClassName(actualType);
		String expectedClassName = getClassName(getClass(expectedValue));
		if (!actualClassName.equals(expectedClassName))
		{
			result.addAll(getContext(actualName + ".class", actualClassName,
				expectedName + ".class", expectedClassName, false));
			return result;
		}
		// Do not use config.toString() for hashCode values because they are not meant for human consumption
		String actualHashCode = String.valueOf(Objects.hashCode(actualValue));
		String expectedHashCode = String.valueOf(Objects.hashCode(expectedValue));
		if (!actualHashCode.equals(expectedHashCode))
		{
			result.addAll(getContext(actualName + ".hashCode", actualHashCode,
				expectedName + ".hashCode", expectedHashCode, false));
			return result;
		}
		String actualIdentityHashCode = String.valueOf(System.identityHashCode(actualValue));
		String expectedIdentityHashCode = String.valueOf(System.identityHashCode(expectedValue));
		result.addAll(getContext(actualName + ".identityHashCode", actualIdentityHashCode,
			expectedName + ".identityHashCode", expectedIdentityHashCode, false));
		return result;
	}
}
