/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.message.diff;

import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.StringMappers;
import io.github.cowwoc.requirements13.java.internal.message.section.ContextSection;
import io.github.cowwoc.requirements13.java.internal.message.section.MessageBuilder;
import io.github.cowwoc.requirements13.java.internal.message.section.MessageSection;
import io.github.cowwoc.requirements13.java.internal.message.section.StringSection;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.SequencedMap;

import static io.github.cowwoc.requirements13.java.internal.util.ValidationTarget.invalid;
import static io.github.cowwoc.requirements13.java.internal.util.ValidationTarget.valid;

/**
 * Generates the contextual information to add to the exception message.
 */
public final class ContextGenerator
{
	private final ApplicationScope scope;
	private final Configuration configuration;
	private final DiffGenerator diffGenerator;
	/**
	 * The name of the actual value.
	 */
	private final String actualName;
	/**
	 * The actual value.
	 */
	private ValidationTarget<Object> actualValue = invalid();
	/**
	 * The name of the expected value.
	 */
	private final String expectedName;
	/**
	 * The expected value.
	 */
	private ValidationTarget<Object> expectedValue = invalid();
	/**
	 * {@code true} if exception messages may include a diff that compares actual and expected values.
	 */
	private boolean allowDiff;
	/**
	 * {@code true} if the output may include an explanation of the diff format.
	 */
	private boolean allowLegend;

	/**
	 * Creates a ContextGenerator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param actualName    the name of the actual value
	 * @param expectedName  the name of the expected value
	 * @throws AssertionError if:
	 *                        <ul>
	 *                          <li>any of the arguments is null</li>
	 *                          <li>{@code actualName} or {@code expectedName} are blank</li>
	 *                          <li>{@code actualName} or {@code expectedName} contains a colon</li>
	 *                        </ul>
	 */
	public ContextGenerator(ApplicationScope scope, Configuration configuration, String actualName,
		String expectedName)
	{
		assert configuration != null : "configuration may not be null";
		assert scope != null : "scope may not be null";

		assert actualName != null : "actualName may not be null";
		assert !actualName.isBlank() : "name may not be blank";
		assert !actualName.contains(":") : "actualName may not contain a colon.\n" +
			"actualName: " + actualName;

		assert expectedName != null : "expectedName may not be null";
		assert !expectedName.isBlank() : "name may not be blank";
		assert !expectedName.contains(":") : "expectedName may not contain a colon.\n" +
			"expectedName: " + expectedName;

		this.scope = scope;
		this.configuration = configuration;
		this.diffGenerator = new DiffGenerator(scope.getGlobalConfiguration().terminalEncoding());
		this.allowDiff = configuration.allowDiff();
		this.actualName = actualName;
		this.expectedName = expectedName;
	}

	/**
	 * Sets the actual value.
	 *
	 * @param value the object representation of the actual value
	 * @return this
	 */
	public ContextGenerator actualValue(Object value)
	{
		actualValue = valid(value);
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
		expectedValue = valid(value);
		return this;
	}

	/**
	 * Overrides the value of {@link Configuration#allowDiff()}.
	 *
	 * @param allowDiff {@code true} if exception messages may include a diff that compares actual and expected
	 *                  values
	 * @return this
	 */
	public ContextGenerator allowDiff(boolean allowDiff)
	{
		this.allowDiff = allowDiff;
		return this;
	}

	/**
	 * Overrides the value of {@link Configuration#allowDiff()}.
	 *
	 * @param allowLegend {@code true} if the output may include an explanation of the diff format
	 * @return this
	 */
	public ContextGenerator allowLegend(boolean allowLegend)
	{
		this.allowLegend = allowLegend;
		return this;
	}

	/**
	 * @return the diff to append to the exception message
	 */
	public List<MessageSection> build()
	{
		assert actualValue.isValid() || expectedValue.isValid() :
			"actualValue and expectedValue were both undefined";

		if (actualValue.map(v -> v instanceof List).or(false) &&
			expectedValue.map(v -> v instanceof List).or(false))
		{
			return getContextOfList();
		}
		return getContextOfObjects();
	}

	/**
	 * @param actualName    the name of the actual value
	 * @param actualValue   the value of the actual value
	 * @param diff          the difference between the two values (empty if absent)
	 * @param expectedName  the name of the expected value
	 * @param expectedValue the value of the expected value
	 * @return the difference between the expected and actual values
	 */
	private MessageSection getDiffSection(String actualName, String actualValue, String diff,
		String expectedName, String expectedValue)
	{
		SequencedMap<String, String> value = LinkedHashMap.newLinkedHashMap(3);

		value.put(actualName, actualValue);
		if (!diff.isEmpty())
			value.put("diff", diff);
		value.put(expectedName, expectedValue);
		return new ContextSection(value);
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
	 * @return the difference between the expected and actual values
	 * @throws AssertionError if the actual or expected values do not exist
	 */
	public List<MessageSection> getContextOfList()
	{
		List<?> actualAsList = valueToList(actualValue.or(null));
		List<?> expectedAsList = valueToList(expectedValue.or(null));
		int actualSize = actualAsList.size();
		int expectedSize = expectedAsList.size();
		int maxSize = Math.max(actualSize, expectedSize);

		List<MessageSection> components = new ArrayList<>();
		// Indicates if the previous index was equal
		boolean skippedEqualElements = false;
		int actualIndex = 0;
		int expectedIndex = 0;
		for (int i = 0; i < maxSize; ++i)
		{
			boolean elementsAreEqual = true;
			boolean actualLineExists = i < actualSize;

			String actualNameLine;
			ValidationTarget<Object> actualValueLine;
			if (actualLineExists)
			{
				actualNameLine = actualName + "[" + actualIndex + "]";
				actualValueLine = valid(actualAsList.get(i));
				++actualIndex;
			}
			else
			{
				actualNameLine = actualName;
				actualValueLine = invalid();
				elementsAreEqual = false;
			}

			boolean expectedLineExists = i < expectedSize;
			String expectedNameLine;
			ValidationTarget<Object> expectedValueLine;
			if (expectedLineExists)
			{
				expectedNameLine = expectedName + "[" + expectedIndex + "]";
				expectedValueLine = valid(expectedAsList.get(i));
				++expectedIndex;
			}
			else
			{
				expectedNameLine = expectedName;
				expectedValueLine = invalid();
				elementsAreEqual = false;
			}
			ContextGenerator elementGenerator = new ContextGenerator(scope, configuration, actualNameLine,
				expectedNameLine).
				allowLegend(false);
			actualValueLine.ifValid(elementGenerator::actualValue);
			expectedValueLine.ifValid(elementGenerator::expectedValue);

			elementsAreEqual = elementsAreEqual && actualValueLine.equals(expectedValueLine);
			if (i != 0 && i != maxSize - 1 && elementsAreEqual)
			{
				// Skip identical elements, unless they are the first or last element.
				skippedEqualElements = true;
				continue;
			}
			if (skippedEqualElements)
			{
				skippedEqualElements = false;
				components.add(skipEqualLines());
			}
			if (!components.isEmpty())
			{
				// Insert an empty line between each diff section
				components.add(new StringSection(""));
			}
			components.addAll(elementGenerator.build());
		}
		return components;
	}

	@SuppressWarnings("unchecked")
	private static List<?> valueToList(Object value)
	{
		return (List<Object>) value;
	}

	/**
	 * Returns context entries to indicate that duplicate lines were skipped.
	 *
	 * @return the context entries to append
	 */
	private static MessageSection skipEqualLines()
	{
		return new StringSection("""
			
			[...]""");
	}

	/**
	 * Generates an exception context from the actual and expected values.
	 *
	 * @return the difference between the expected and actual values
	 */
	private List<MessageSection> getContextOfObjects()
	{
		assert actualValue.isValid() || expectedValue.isValid() :
			"actualValue and expectedValue were both undefined";

		// Calculate the diff
		StringMappers stringMappers = configuration.stringMappers();
		String actualAsString = actualValue.map(stringMappers::toString).or("");
		String expectedAsString = expectedValue.map(stringMappers::toString).or("");
		DiffResult lines = diffGenerator.diff(actualAsString, expectedAsString);

		// Don't show diff lines for boolean values
		if (actualValue.map(v -> v instanceof Boolean).or(false) ||
			expectedValue.map(v -> v instanceof Boolean).or(false))
		{
			return getContextForSingleLine(lines);
		}

		List<MessageSection> context = new ArrayList<>();
		context.addAll(diffToMessageSections(lines));
		context.addAll(compareTypes(lines));
		return context;
	}

	/**
	 * @param lines the difference between the actual and expected value
	 * @return the difference represented as an exception message
	 */
	private List<MessageSection> diffToMessageSections(DiffResult lines)
	{
		// When comparing multiline strings, this method is invoked one line at a time. If the actual or expected
		// value is invalid, it indicates that one of the values contains more lines than the other. The value
		// with fewer lines will be considered invalid on a per-line basis.
		int numberOfLines = Math.max(lines.getActualLines().size(), lines.getExpectedLines().size());
		if (!allowDiff || numberOfLines == 1)
			return getContextForSingleLine(lines);

		int actualLineNumber = 0;
		int expectedLineNumber = 0;
		List<String> actualLines = lines.getActualLines();
		List<String> expectedLines = lines.getExpectedLines();
		List<Boolean> equalLines = lines.getEqualLines();
		boolean diffLinesExist = !lines.getDiffLines().isEmpty();

		// Indicates if the previous line was equal
		boolean skippedEqualLines = false;
		List<MessageSection> context = new ArrayList<>();
		for (int i = 0; i < numberOfLines; ++i)
		{
			boolean valuesAreEqual = equalLines.get(i);
			if (i != 0 && i != numberOfLines - 1 && valuesAreEqual)
			{
				// Skip equal lines, unless they are the first or last line.
				skippedEqualLines = true;
				++actualLineNumber;
				++expectedLineNumber;
				continue;
			}

			String actualValueLine = getElementOrEmptyString(actualLines, i);
			String actualNameLine;
			if (diffGenerator.isEmpty(actualValueLine))
				actualNameLine = actualName;
			else
			{
				actualNameLine = actualName + "@" + actualLineNumber;
				if (DiffConstants.EOL_PATTERN.matcher(actualValueLine).find())
					++actualLineNumber;
			}

			String diffLine;
			if (diffLinesExist && !valuesAreEqual)
				diffLine = lines.getDiffLines().get(i);
			else
				diffLine = "";

			String expectedValueLine = getElementOrEmptyString(expectedLines, i);
			String expectedNameLine;
			if (diffGenerator.isEmpty(expectedValueLine))
				expectedNameLine = expectedName;
			else
			{
				expectedNameLine = expectedName + "@" + expectedLineNumber;
				if (DiffConstants.EOL_PATTERN.matcher(expectedValueLine).find())
					++expectedLineNumber;
			}
			if (skippedEqualLines)
			{
				skippedEqualLines = false;
				context.add(skipEqualLines());
			}

			if (!context.isEmpty())
				context.add(new StringSection(""));
			ContextGenerator elementGenerator = new ContextGenerator(scope, configuration, actualNameLine,
				expectedNameLine).
				actualValue(actualValueLine).
				expectedValue(expectedValueLine);
			context.add(elementGenerator.getDiffSection(actualNameLine, actualValueLine, diffLine, expectedNameLine,
				expectedValueLine));
		}
		if (diffLinesExist && this.allowLegend)
			context.add(new StringSection(MessageBuilder.DIFF_LEGEND));
		return context;
	}

	/**
	 * @param list a list
	 * @param i    an index
	 * @return the element at the specified index, or {@code ""} if the index is out of bounds
	 */
	private static String getElementOrEmptyString(List<String> list, int i)
	{
		if (list.size() > i)
			return list.get(i);
		return "";
	}

	private List<MessageSection> getContextForSingleLine(DiffResult lines)
	{
		String actualAsString;
		String expectedAsString;
		if (lines.getActualLines().size() > 1 || lines.getExpectedLines().size() > 1)
		{
			StringMappers stringMappers = configuration.stringMappers();
			actualAsString = actualValue.map(stringMappers::toString).or("");
			expectedAsString = expectedValue.map(stringMappers::toString).or("");
		}
		else
		{
			actualAsString = lines.getActualLines().getFirst();
			expectedAsString = lines.getExpectedLines().getFirst();
		}

		boolean diffLinesExist = !lines.getDiffLines().isEmpty();
		boolean valuesAreEqual = lines.getEqualLines().getFirst();

		String diffLine;
		if (diffLinesExist && !valuesAreEqual)
			diffLine = lines.getDiffLines().getFirst();
		else
			diffLine = "";

		// We need to check if the values are equal because some collection elements may be equal even if the
		// overall collections differ.
		List<MessageSection> context = new ArrayList<>();
		context.add(getDiffSection(actualName, actualAsString, diffLine, expectedName, expectedAsString));
		context.addAll(compareTypes(lines));
		return context;
	}

	/**
	 * @param lines the result of comparing the actual and expected values
	 * @return {@code true} if the string representation of the values is equal
	 */
	private boolean stringRepresentationsAreEqual(DiffResult lines)
	{
		return lines.getEqualLines().stream().allMatch(isEqual -> isEqual);
	}

	/**
	 * If the values differ but their string representation is the same adds lines which compare getClass(),
	 * hashCode() and/or System.identityHashCode() to figure out why they differ.
	 *
	 * @param lines the difference between the actual and expected value
	 * @return an empty List if the values are equal or the string representations differ
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<MessageSection> compareTypes(DiffResult lines)
	{
		// We need to check if the values are equal because some collection elements may be equal even if the
		// overall collections differ.
		if (actualValue.equals(expectedValue) || !stringRepresentationsAreEqual(lines))
			return List.of();

		assert actualValue.isValid() : "actualValue was undefined";
		assert expectedValue.isValid() : "expectedValue was undefined";
		Object actualValueOrNull = actualValue.orThrow(AssertionError::new);
		Object expectedValueOrNull = expectedValue.orThrow(AssertionError::new);

		String actualClassName = getClassName(getClass(actualValueOrNull));
		String expectedClassName = getClassName(getClass(expectedValueOrNull));
		if (!actualClassName.equals(expectedClassName))
		{
			List<MessageSection> context = new ArrayList<>();
			context.add(new StringSection(""));
			context.addAll(
				new ContextGenerator(scope, configuration, actualName + ".class", expectedName + ".class").
					actualValue(actualClassName).
					expectedValue(expectedClassName).
					allowDiff(false).
					build());
			return context;
		}
		// Do not use config.toString() for hashCode values because their exact value does not matter, just the
		// fact that they are different.
		int actualHashCode = Objects.hashCode(actualValueOrNull);
		int expectedHashCode = Objects.hashCode(expectedValueOrNull);
		if (actualHashCode != expectedHashCode)
		{
			List<MessageSection> context = new ArrayList<>();
			context.add(new StringSection(""));
			context.addAll(new ContextGenerator(scope, configuration, actualName + ".hashCode",
				expectedName + ".hashCode").
				actualValue(actualHashCode).
				expectedValue(expectedHashCode).
				allowDiff(false).
				build());
			return context;
		}
		int actualIdentityHashCode = System.identityHashCode(actualValueOrNull);
		int expectedIdentityHashCode = System.identityHashCode(expectedValueOrNull);
		if (actualIdentityHashCode != expectedIdentityHashCode)
		{
			List<MessageSection> context = new ArrayList<>();
			context.add(new StringSection(""));
			context.addAll(new ContextGenerator(scope, configuration, actualName + ".identityHashCode",
				expectedName + ".identityHashCode").
				actualValue(actualIdentityHashCode).
				expectedValue(expectedIdentityHashCode).
				allowDiff(false).
				build());
			return context;
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
		StringBuilder result = new StringBuilder(77).
			append("actualName: ").append(actualName);
		actualValue.ifValid(v -> result.append(", actualValue: ").append(v));
		result.append("expectedName: ").append(expectedName);
		expectedValue.ifValid(v -> result.append(", expectedValue: ").append(v));
		result.append(", allowDiff: ").append(allowDiff);
		return result.toString();
	}
}