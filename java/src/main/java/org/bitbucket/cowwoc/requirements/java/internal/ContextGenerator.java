/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffGenerator;
import org.bitbucket.cowwoc.requirements.java.internal.diff.DiffResult;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.DIFF_EQUAL;
import static org.bitbucket.cowwoc.requirements.java.internal.diff.DiffConstants.EOL_PATTERN;

/**
 * Returns the difference between two values as an exception context.
 */
public final class ContextGenerator
{
	private static final Pattern ELEMENTS_NOT_EQUAL = Pattern.compile("[^" + DIFF_EQUAL + "]+");

	/**
	 * Append context entries to indicate that duplicate elements were skipped.
	 *
	 * @param entries the exception context
	 */
	private static void skipDuplicateElements(List<ContextLine> entries)
	{
		entries.add(new ContextLine(""));
		entries.add(new ContextLine("[...]"));
	}

	/**
	 * @param actualElements   the actual elements
	 * @param expectedElements the expected elements
	 * @param diffElements     the middle elements
	 * @param index            the index of the current element (0-based)
	 * @return true if the elements being compared are equal to each other
	 */
	private static boolean elementsAreEqual(List<?> actualElements, List<?> expectedElements,
	                                        List<String> diffElements, int index)
	{
		if (!diffElements.isEmpty())
			return !ELEMENTS_NOT_EQUAL.matcher(diffElements.get(index)).find();
		return actualElements.get(index).equals(expectedElements.get(index));
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
		assert (actualName != null) : "actualName may not be null";
		// actualType is null if actualValue is null
		assert (expectedName != null) : "expectedName may not be null";
		Class<?> actualType = getClass(actualValue);
		return getContext(actualName, actualValue, actualType, expectedName, expectedValue, expectedInMessage);
	}

	/**
	 * @param actualName        the name of the actual value
	 * @param actualType        the type of the actual value
	 * @param actualValue       the actual value
	 * @param expectedName      the name of the expected value
	 * @param expectedValue     the expected value
	 * @param expectedInMessage true if the expected value is already mentioned in the failure message
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<ContextLine> getContext(String actualName, Object actualValue, Class<?> actualType,
	                                     String expectedName, Object expectedValue,
	                                     boolean expectedInMessage)
	{
		// Don't diff booleans
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
		DiffResult diff = diffGenerator.diff(actualAsString, expectedAsString);
		return getContext(actualName, diff.getActualLines(), expectedName, diff.getExpectedLines(),
			diff.getDiffLines(), diffGenerator::isEmpty);
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
		// actualType is null if actualValue is null
		assert (expectedName != null) : "expectedName may not be null";
		// Don't diff booleans
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
		int actualElementNumber = 0;
		int expectedElementNumber = 0;
		for (int i = 0; i < maxSize; ++i)
		{
			boolean elementsAreEqual = true;
			String actualValueAsString;
			String actualNameForElement;
			if (i < actualSize)
			{
				actualValueAsString = config.toString(actualValue.get(i));
				actualNameForElement = actualName + "[" + actualElementNumber + "]";
				++actualElementNumber;
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
				expectedNameForElement = expectedName + "[" + expectedElementNumber + "]";
				++expectedElementNumber;
			}
			else
			{
				expectedValueAsString = "";
				expectedNameForElement = expectedName;
				elementsAreEqual = false;
			}
			elementsAreEqual &= actualValue.get(i).equals(expectedValue.get(i));
			if (i != 0 && i != maxSize - 1 && elementsAreEqual)
			{
				// Skip identical elements, unless they are the first or last element.
				skippedDuplicates = true;
				continue;
			}
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateElements(result);
			}
			result.addAll(getContext(actualNameForElement, actualValueAsString, expectedNameForElement,
				expectedValueAsString, false));
		}
		return result;
	}

	/**
	 * @param actualName       the name of the actual value
	 * @param actualElements   the elements in the actual value
	 * @param expectedName     the name of the expected value
	 * @param expectedElements the elements in the expected value
	 * @param diffElements     the elements denoting the difference between the actual and expected elements
	 * @param isEmpty          indicates if an element represents an empty value
	 * @return the list of name-value pairs to append to the exception message
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<ContextLine> getContext(String actualName, List<?> actualElements,
	                                     String expectedName, List<?> expectedElements,
	                                     List<String> diffElements, Predicate<String> isEmpty)
	{
		// Outputs the String representation of the values. If those are equal, it also outputs the first of
		// getClass(), hashCode(), or System.identityHashCode()] that differs.
		int numberOfElements = actualElements.size();
		List<ContextLine> result = new ArrayList<>(2 * numberOfElements);
		if (numberOfElements == 1)
		{
			result.add(new ContextLine(""));
			result.add(new ContextLine(actualName, actualElements.get(0)));
			if (!diffElements.isEmpty() && !elementsAreEqual(actualElements, expectedElements, diffElements, 0))
				result.add(new ContextLine("Diff", diffElements.get(0)));
			result.add(new ContextLine(expectedName, expectedElements.get(0)));
			return result;
		}
		assert (expectedElements.size() == numberOfElements) :
			"elements: " + numberOfElements + ", expected.size(): " +
				expectedElements.size();
		int actualElementNumber = 0;
		int expectedElementNumber = 0;
		// Indicates if the previous index was equal
		boolean skippedDuplicates = false;
		for (int i = 0; i < numberOfElements; ++i)
		{
			Object actualElement = actualElements.get(i);
			Object expectedElement = expectedElements.get(i);
			boolean elementsAreEqual = elementsAreEqual(actualElements, expectedElements, diffElements, i);
			if (i != 0 && i != numberOfElements - 1 && elementsAreEqual)
			{
				// Skip identical elements, unless they are the first or last element.
				skippedDuplicates = true;
				++actualElementNumber;
				++expectedElementNumber;
				continue;
			}
			Class<?> actualElementType = actualElement.getClass();
			Class<?> expectedElementType = expectedElement.getClass();

			String actualElementAsString = config.toString(actualElement);
			String actualNameForElement;
			if (!isEmpty.test(actualElementAsString))
			{
				actualNameForElement = actualName + "@" + actualElementNumber;
				if (EOL_PATTERN.matcher(actualElementAsString).find())
					++actualElementNumber;
			}
			else
				actualNameForElement = actualName;
			if (skippedDuplicates)
			{
				skippedDuplicates = false;
				skipDuplicateElements(result);
			}
			result.add(new ContextLine(""));
			result.add(new ContextLine(actualNameForElement, actualElement));
			if (!diffElements.isEmpty() && !elementsAreEqual)
				result.add(new ContextLine("Diff", diffElements.get(i)));

			String expectedElementAsString = config.toString(expectedElement);
			String expectedNameForElement;
			if (!isEmpty.test(expectedElementAsString))
			{
				expectedNameForElement = expectedName + "@" + expectedElementNumber;
				if (EOL_PATTERN.matcher(expectedElementAsString).find())
					++expectedElementNumber;
			}
			else
				expectedNameForElement = expectedName;
			result.add(new ContextLine(expectedNameForElement, expectedElement));
			if (elementsAreEqual && !actualElement.equals(expectedElement))
			{
				result.add(new ContextLine(""));
				result.addAll(compareTypes(actualName, actualElement, config.toString(actualElement), expectedName,
					expectedElement, config.toString(expectedElement)));
			}
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
	private List<ContextLine> compareTypes(String actualName, Object actualValue,
	                                       String actualAsString, String expectedName,
	                                       Object expectedValue, String expectedAsString)
	{
		List<ContextLine> result = new ArrayList<>();
		Class<?> actualType = getClass(actualValue);
		String actualClassName = getClassName(actualType);
		String expectedClassName = getClassName(getClass(expectedValue));
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
		return result;
	}
}
