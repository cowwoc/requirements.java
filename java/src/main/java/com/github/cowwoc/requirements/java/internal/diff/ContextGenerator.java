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

import static com.github.cowwoc.requirements.java.internal.diff.DiffConstants.EOL_PATTERN;

/**
 * Returns the difference between two values as an exception context.
 */
public final class ContextGenerator
{
	private final Configuration config;
	private final DiffGenerator diffGenerator;
	private final ApplicationScope scope;
	private String actualName = "";
	private boolean actualExists;
	private Object actualValue;
	private String expectedName = "";
	private boolean expectedExists;
	private Object expectedValue;
	private boolean expectedInMessage;
	private boolean compareValues = true;

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
		this.scope = scope;
		this.diffGenerator = new DiffGenerator(scope.getGlobalConfiguration().getTerminalEncoding());
	}

	/**
	 * Sets the name of the actual value.
	 *
	 * @param name the name of the actual value
	 * @return this
	 * @throws AssertionError if {@code name} is null, blank or contains a colon
	 */
	public ContextGenerator actualName(String name)
	{
		assert (name != null);
		assert (!name.isBlank()) : "name may not be blank";
		assert (!name.contains(":")) : "name may not contain a colon.\n" +
			"Actual: " + name;
		actualName = name;
		return this;
	}

	/**
	 * Sets the actual value.
	 *
	 * @param value the actual value
	 * @return this
	 */
	public ContextGenerator actualValue(Object value)
	{
		actualExists = true;
		actualValue = value;
		return this;
	}

	/**
	 * Sets the name of the expected value.
	 *
	 * @param name the name of the actual value
	 * @return this
	 * @throws AssertionError if {@code name} is null, blank or contains a colon
	 */
	public ContextGenerator expectedName(String name)
	{
		assert (name != null);
		assert (!name.isBlank()) : "name may not be blank";
		assert (!name.contains(":")) : "name may not contain a colon.\n" +
			"Actual: " + name;
		expectedName = name;
		return this;
	}

	/**
	 * Sets the expected value.
	 *
	 * @param value the actual value
	 * @return this
	 */
	public ContextGenerator expectedValue(Object value)
	{
		expectedExists = true;
		expectedValue = value;
		return this;
	}

	/**
	 * Indicates that the expected value shows up in the exception message.
	 *
	 * @return this
	 */
	public ContextGenerator expectedInMessage()
	{
		expectedInMessage = true;
		return this;
	}

	/**
	 * Indicates that the expected and actual values should not be compared. This flag is set automatically
	 * if either {@code actual} or {@code expected} are a boolean.
	 *
	 * @return this
	 */
	public ContextGenerator doNotCompareValues()
	{
		compareValues = false;
		return this;
	}

	/**
	 * @return the list of name-value pairs to append to the exception message
	 */
	public List<ContextLine> build()
	{
		Class<?> expectedType = ContextGenerator.getClass(expectedValue);
		Class<?> actualType = ContextGenerator.getClass(actualValue);
		compareValues &= !(expectedType == boolean.class || expectedType == Boolean.class) &&
			!(actualType == boolean.class || actualType == Boolean.class);

		if (!config.isDiffEnabled() || !compareValues)
		{
			List<ContextLine> context = new ArrayList<>(2);
			context.add(new ContextLine("", "", true));
			context.add(new ContextLine(actualName, actualValue, false));
			if (!expectedInMessage)
				context.add(new ContextLine(expectedName, expectedValue, false));
			return context;
		}

		if (actualValue instanceof List && expectedValue instanceof List)
			return getContextOfList();
		return getContextOfObjects();
	}

	/**
	 * Append context entries to indicate that duplicate lines were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipEqualLines(List<ContextLine> entries)
	{
		entries.add(new ContextLine("", "", true));
		entries.add(new ContextLine("", "[...]", true));
	}

	/**
	 * Generates a List-specific exception context from the actual and expected values.
	 *
	 * @return the name-value pairs to append to the exception message
	 * @throws AssertionError if the actual or expected values do not exist.
	 */
	private List<ContextLine> getContextOfList()
	{
		assert (actualExists && expectedExists) :
			"actualExists: " + actualExists + ", expectedExists: " + expectedExists;
		@SuppressWarnings("unchecked")
		List<?> actualList = (List<Object>) actualValue;
		int actualSize = actualList.size();
		@SuppressWarnings("unchecked")
		List<?> expectedList = (List<Object>) expectedValue;
		int expectedSize = expectedList.size();
		int maxSize = Math.max(actualSize, expectedSize);

		List<ContextLine> context = new ArrayList<>();
		// Indicates if the previous index was equal
		boolean skippedEqualElements = false;
		int actualIndex = 0;
		int expectedIndex = 0;
		for (int i = 0; i < maxSize; ++i)
		{
			ContextGenerator elementGenerator = new ContextGenerator(config, scope);
			boolean actualLineExists = i < actualSize;

			boolean elementsAreEqual = true;
			Object actualLineValue;
			if (actualLineExists)
			{
				elementGenerator.actualName(actualName + "[" + actualIndex + "]");
				actualLineValue = actualList.get(i);
				elementGenerator.actualValue(actualLineValue);
				++actualIndex;
			}
			else
			{
				elementGenerator.actualName(actualName);
				elementsAreEqual = false;
				actualLineValue = "";
			}

			boolean expectedLineExists = i < expectedSize;

			Object expectedLineValue;
			if (expectedLineExists)
			{
				elementGenerator.expectedName(expectedName + "[" + expectedIndex + "]");
				expectedLineValue = expectedList.get(i);
				elementGenerator.expectedValue(expectedLineValue);
				++expectedIndex;
			}
			else
			{
				elementGenerator.expectedName(expectedName);
				elementsAreEqual = false;
				expectedLineValue = "";
			}

			elementsAreEqual &= actualLineValue.equals(expectedLineValue);
			if (i != 0 && i != maxSize - 1 && elementsAreEqual)
			{
				// Skip identical elements, unless they are the first or last element.
				skippedEqualElements = true;
				continue;
			}
			if (skippedEqualElements)
			{
				skippedEqualElements = false;
				skipEqualLines(context);
			}
			context.addAll(elementGenerator.build());
		}
		return context;
	}

	/**
	 * Generates an exception context from the actual and expected values.
	 *
	 * @return the list of name-value pairs to append to the exception message
	 */
	private List<ContextLine> getContextOfObjects()
	{
		String actualAsString;
		if (actualExists)
			actualAsString = config.toString(actualValue);
		else
			actualAsString = "";

		String expectedAsString;
		if (expectedExists)
			expectedAsString = config.toString(expectedValue);
		else
			expectedAsString = "";

		if (!compareValues)
		{
			List<ContextLine> result = new ArrayList<>(2);
			result.add(new ContextLine(actualName, actualAsString, true));
			if (!expectedInMessage)
				result.add(new ContextLine(expectedName, expectedAsString, true));
			return result;
		}
		DiffResult lines = diffGenerator.diff(actualAsString, expectedAsString);
		int numberOfLines = lines.getActualLines().size();
		boolean diffLinesExist = !lines.getDiffLines().isEmpty();

		List<ContextLine> result = new ArrayList<>(2 * numberOfLines);
		if (numberOfLines == 1)
		{
			String actualLine = lines.getActualLines().get(0);
			String expectedLine = lines.getExpectedLines().get(0);
			boolean linesAreEqual = lines.getEqualLines().get(0);
			result.add(new ContextLine("", "", true));
			result.add(new ContextLine(actualName, actualLine, true));
			if (diffLinesExist && !linesAreEqual)
			{
				String diffLine = lines.getDiffLines().get(0);
				result.add(new ContextLine("Diff", diffLine, true));
			}
			result.add(new ContextLine(expectedName, expectedLine, true));

			if (compareValues && linesAreEqual)
			{
				// If the String representation of the values is equal, output getClass(), hashCode(),
				// or System.identityHashCode()] that differ.
				result.addAll(compareTypes());
			}
			return result;
		}
		int actualLineNumber = 0;
		int expectedLineNumber = 0;
		List<String> actualLines = lines.getActualLines();
		List<String> expectedLines = lines.getExpectedLines();
		List<Boolean> equalLines = lines.getEqualLines();
		// Indicates if the previous line was equal
		boolean skippedEqualLines = false;
		for (int i = 0; i < numberOfLines; ++i)
		{
			String actualLine = actualLines.get(i);
			String expectedLine;
			if (expectedLines.size() > i)
				expectedLine = expectedLines.get(i);
			else
				expectedLine = "";
			boolean valuesAreEqual = equalLines.get(i);
			if (i != 0 && i != numberOfLines - 1 && valuesAreEqual)
			{
				// Skip equal lines, unless they are the first or last line.
				skippedEqualLines = true;
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
			if (skippedEqualLines)
			{
				skippedEqualLines = false;
				skipEqualLines(result);
			}
			result.add(new ContextLine("", "", true));
			result.add(new ContextLine(actualNameForLine, actualLine, true));
			if (diffLinesExist && !valuesAreEqual)
			{
				String diffLine = lines.getDiffLines().get(i);
				result.add(new ContextLine("Diff", diffLine, true));
			}

			String expectedNameForLine;
			if (diffGenerator.isEmpty(expectedLine))
				expectedNameForLine = expectedName;
			else
			{
				expectedNameForLine = expectedName + "@" + expectedLineNumber;
				if (EOL_PATTERN.matcher(expectedLine).find())
					++expectedLineNumber;
			}
			result.add(new ContextLine(expectedNameForLine, expectedLine, true));
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
		if (type == null) return "null";
		return type.getName();
	}

	/**
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<ContextLine> compareTypes()
	{
		assert (actualExists && expectedExists) : this;

		List<ContextLine> result = new ArrayList<>();
		Class<?> actualType = getClass(actualValue);
		String actualClassName = getClassName(actualType);
		String expectedClassName = getClassName(getClass(expectedValue));
		if (!actualClassName.equals(expectedClassName))
		{
			result.addAll(new ContextGenerator(config, scope).
				actualName(actualName + ".class").
				actualValue(config.toString(actualClassName)).
				expectedName(expectedName + ".class").
				expectedValue(config.toString(expectedClassName)).
				doNotCompareValues().build());
			return result;
		}
		// Do not use config.toString() for hashCode values because their exact value does not matter, just the
		// fact that they are different.
		int actualHashCode = Objects.hashCode(actualValue);
		int expectedHashCode = Objects.hashCode(expectedValue);
		if (actualHashCode != expectedHashCode)
		{
			result.addAll(new ContextGenerator(config, scope).
				actualName(actualName + ".hashCode").
				actualValue(actualHashCode).
				expectedName(expectedName + ".hashCode").
				expectedValue(expectedHashCode).
				doNotCompareValues().build());
			return result;
		}
		int actualIdentityHashCode = System.identityHashCode(actualValue);
		int expectedIdentityHashCode = System.identityHashCode(expectedValue);
		if (actualIdentityHashCode != expectedIdentityHashCode)
		{
			result.addAll(new ContextGenerator(config, scope).
				actualName(actualName + ".identityHashCode").
				actualValue(actualIdentityHashCode).
				expectedName(expectedName + ".identityHashCode").
				expectedValue(expectedIdentityHashCode).
				doNotCompareValues().build());
		}
		return result;
	}

	@Override
	public String toString()
	{
		return "actualName: " + actualName + ", actualExists: " + actualExists + ", actualValue: " + actualValue +
			", expectedName: " + expectedName + ", actualExists: " + expectedExists + ", expectedValue: " +
			expectedValue + ", expectedInMessage: " + expectedInMessage + ", compareValues: " + compareValues;
	}
}