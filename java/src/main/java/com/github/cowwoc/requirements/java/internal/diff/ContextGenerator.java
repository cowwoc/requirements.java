/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.diff;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.StringMappers;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
	private String expectedName = "";
	private boolean expectedExists;
	private Object expectedValue;
	private boolean expectedInMessage;
	private String actualName = "";
	private boolean actualExists;
	private Object actualValue;
	private boolean compareValues = true;

	/**
	 * @param configuration the validator configuration
	 * @param scope         the application configuration
	 * @throws AssertionError if {@code configuration} or {@code scope} are null
	 */
	public ContextGenerator(Configuration configuration, ApplicationScope scope)
	{
		assert (configuration != null) : "configuration may not be null";
		assert (scope != null) : "scope may not be null";
		this.config = configuration;
		this.scope = scope;
		this.diffGenerator = new DiffGenerator(scope.getGlobalConfiguration().terminalEncoding());
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
	 * @param value the object representation of the actual value
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
	 * @param name the name of the expected value
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
	 * @param value the object representation of the expected value
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
	 * Indicates that the expected and actual values should not be compared. This flag is set automatically if
	 * either {@code actual} or {@code expected} are a boolean.
	 *
	 * @return this
	 */
	public ContextGenerator doNotCompareValues()
	{
		compareValues = false;
		return this;
	}

	/**
	 * @return a list of DIFF sections to append to the exception message
	 */
	public List<Map<String, Object>> build()
	{
		Class<?> expectedType = ContextGenerator.getClass(expectedValue);
		// On a line-by-line basis, "actualExists" might be false.
		if (actualExists)
		{
			Class<?> actualType = ContextGenerator.getClass(actualValue);
			compareValues &= actualType != Boolean.class && expectedType != Boolean.class;
		}

		if (!config.includeDiff() || !compareValues)
		{
			Map<String, Object> context = new LinkedHashMap<>();
			StringMappers stringMappers = config.stringMappers();
			if (!expectedInMessage)
				context.put(expectedName, stringMappers.toString(expectedValue));
			if (actualExists)
				context.put(actualName, stringMappers.toString(actualValue));
			return List.of(context);
		}

		if ((!actualExists || actualValue instanceof List) && expectedValue instanceof List)
			return getContextOfList();
		return getContextOfObjects();
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
	 * Generates a List-specific exception context from the actual and expected values.
	 *
	 * @return a list of DIFF sections to append to the exception message
	 * @throws AssertionError if the actual or expected values do not exist.
	 */
	private List<Map<String, Object>> getContextOfList()
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

		List<Map<String, Object>> context = new ArrayList<>();
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
	 * Append context entries to indicate that duplicate lines were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipEqualLines(List<Map<String, Object>> entries)
	{
		entries.add(Map.of());
		entries.add(Map.of("", "[...]"));
	}

	/**
	 * Generates an exception context from the actual and expected values.
	 *
	 * @return a list of DIFF sections to append to the exception message
	 */
	private List<Map<String, Object>> getContextOfObjects()
	{
		String actualAsString = getActualAsString();
		String expectedAsString = getExpectedAsString();

		if (!compareValues)
			return getContextWithoutComparison(actualAsString, expectedAsString);
		DiffResult lines = diffGenerator.diff(actualAsString, expectedAsString);
		int numberOfLines = lines.getActualLines().size();
		boolean diffLinesExist = !lines.getDiffLines().isEmpty();

		if (numberOfLines == 1)
			return getContextForSingleLine(lines, diffLinesExist);
		int actualLineNumber = 0;
		int expectedLineNumber = 0;
		List<String> actualLines = lines.getActualLines();
		List<String> expectedLines = lines.getExpectedLines();
		List<Boolean> equalLines = lines.getEqualLines();

		// Indicates if the previous line was equal
		boolean skippedEqualLines = false;
		List<Map<String, Object>> context = new ArrayList<>(2 * numberOfLines);
		for (int i = 0; i < numberOfLines; ++i)
		{
			String actualLine = actualLines.get(i);
			String expectedLine = getExpectedLines(expectedLines, i);
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
				skipEqualLines(context);
			}
			context.add(Map.of());

			String expectedNameForLine;
			if (diffGenerator.isEmpty(expectedLine))
				expectedNameForLine = expectedName;
			else
			{
				expectedNameForLine = expectedName + "@" + expectedLineNumber;
				if (EOL_PATTERN.matcher(expectedLine).find())
					++expectedLineNumber;
			}

			Map<String, Object> section = new LinkedHashMap<>();
			section.put(expectedNameForLine, expectedLine);
			if (diffLinesExist && !valuesAreEqual)
			{
				String diffLine = lines.getDiffLines().get(i);
				section.put("Diff", diffLine);
			}
			section.put(actualNameForLine, actualLine);
			context.add(section);
		}
		return context;
	}

	private static String getExpectedLines(List<String> expectedLines, int i)
	{
		String expectedLine;
		if (expectedLines.size() > i)
			expectedLine = expectedLines.get(i);
		else
			expectedLine = "";
		return expectedLine;
	}

	private List<Map<String, Object>> getContextForSingleLine(DiffResult lines, boolean diffLinesExist)
	{
		String actualLine = lines.getActualLines().getFirst();
		String expectedLine = lines.getExpectedLines().getFirst();
		boolean linesAreEqual = lines.getEqualLines().getFirst();

		List<Map<String, Object>> context = new ArrayList<>();
		context.add(Map.of());

		Map<String, Object> section = new LinkedHashMap<>();
		section.put(expectedName, expectedLine);
		if (diffLinesExist && !linesAreEqual)
			section.put("Diff", lines.getDiffLines().getFirst());
		section.put(actualName, actualLine);
		context.add(section);

		if (compareValues && linesAreEqual)
		{
			// If the String representation of the values is equal, output getClass(), hashCode(),
			// or System.identityHashCode()] that differ.
			context.addAll(compareTypes());
		}
		return context;
	}

	private List<Map<String, Object>> getContextWithoutComparison(String actualAsString,
		String expectedAsString)
	{
		Map<String, Object> context = new LinkedHashMap<>();
		if (!expectedInMessage)
			context.put(expectedName, expectedAsString);
		context.put(actualName, actualAsString);
		return List.of(context);
	}

	private String getExpectedAsString()
	{
		String expectedAsString;
		if (expectedExists)
			expectedAsString = config.stringMappers().toString(expectedValue);
		else
			expectedAsString = "";
		return expectedAsString;
	}

	private String getActualAsString()
	{
		String actualAsString;
		if (actualExists)
			actualAsString = config.stringMappers().toString(actualValue);
		else
			actualAsString = "";
		return actualAsString;
	}

	/**
	 * @return a list of DIFF sections to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<Map<String, Object>> compareTypes()
	{
		assert (actualExists && expectedExists) : this;

		Class<?> actualType = getClass(actualValue);
		String actualClassName = getClassName(actualType);
		String expectedClassName = getClassName(getClass(expectedValue));
		if (!actualClassName.equals(expectedClassName))
		{
			StringMappers stringMappers = config.stringMappers();
			return new ContextGenerator(config, scope).
				expectedName(expectedName + ".class").
				expectedValue(stringMappers.toString(expectedClassName)).
				actualName(actualName + ".class").
				actualValue(stringMappers.toString(actualClassName)).
				doNotCompareValues().build();
		}
		// Do not use config.toString() for hashCode values because their exact value does not matter, just the
		// fact that they are different.
		int actualHashCode = Objects.hashCode(actualValue);
		int expectedHashCode = Objects.hashCode(expectedValue);
		if (actualHashCode != expectedHashCode)
		{
			return new ContextGenerator(config, scope).
				expectedName(expectedName + ".hashCode").
				expectedValue(expectedHashCode).
				actualName(actualName + ".hashCode").
				actualValue(actualHashCode).
				doNotCompareValues().build();
		}
		int expectedIdentityHashCode = System.identityHashCode(expectedValue);
		int actualIdentityHashCode = System.identityHashCode(actualValue);
		if (actualIdentityHashCode != expectedIdentityHashCode)
		{
			return new ContextGenerator(config, scope).
				expectedName(expectedName + ".identityHashCode").
				expectedValue(expectedIdentityHashCode).
				actualName(actualName + ".identityHashCode").
				actualValue(actualIdentityHashCode).
				doNotCompareValues().build();
		}
		return List.of();
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

	@Override
	public String toString()
	{
		return "actualName: " + actualName + ", actualExists: " + actualExists + ", actualValue: " + actualValue +
			", expectedName: " + expectedName + ", actualExists: " + expectedExists + ", expectedValue: " +
			expectedValue + ", expectedInMessage: " + expectedInMessage + ", compareValues: " + compareValues;
	}
}