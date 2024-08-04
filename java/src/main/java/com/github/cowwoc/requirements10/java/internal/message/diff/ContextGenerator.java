/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.message.diff;

import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.StringMappers;
import com.github.cowwoc.requirements10.java.internal.message.section.ContextSection;
import com.github.cowwoc.requirements10.java.internal.message.section.MessageSection;
import com.github.cowwoc.requirements10.java.internal.message.section.StringSection;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.SequencedMap;
import java.util.function.Function;

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
	private MaybeUndefined<Object> actualValue = MaybeUndefined.undefined();
	/**
	 * The name of the expected value.
	 */
	private final String expectedName;
	/**
	 * The expected value.
	 */
	private MaybeUndefined<Object> expectedValue = MaybeUndefined.undefined();
	/**
	 * {@code true} if exception messages may include a diff that compares actual and expected values.
	 */
	private boolean allowDiff;

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
		assert (configuration != null) : "configuration may not be null";
		assert (scope != null) : "scope may not be null";

		assert (actualName != null) : "actualName may not be null";
		assert (!actualName.isBlank()) : "name may not be blank";
		assert (!actualName.contains(":")) : "actualName may not contain a colon.\n" +
			"actualName: " + actualName;

		assert (expectedName != null) : "expectedName may not be null";
		assert (!expectedName.isBlank()) : "name may not be blank";
		assert (!expectedName.contains(":")) : "expectedName may not contain a colon.\n" +
			"expectedName: " + expectedName;

		this.scope = scope;
		this.configuration = configuration;
		this.diffGenerator = new DiffGenerator(scope.getGlobalConfiguration().terminalEncoding());
		this.allowDiff = this.configuration.allowDiff();
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
		actualValue = MaybeUndefined.defined(value);
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
		expectedValue = MaybeUndefined.defined(value);
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
	 * @return the diff to append to the exception message
	 */
	public List<MessageSection> build()
	{
		assert actualValue.isDefined() || expectedValue.isDefined() :
			"actualValue and expectedValue were both undefined";

		if (actualValue.mapDefined(v -> v instanceof List).orDefault(false) ||
			(expectedValue.mapDefined(v -> v instanceof List).orDefault(false)))
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
		Function<Object, List<?>> valueToList = value ->
		{
			@SuppressWarnings("unchecked")
			List<?> temp = (List<Object>) value;
			return temp;
		};
		List<?> actualAsList = actualValue.mapDefined(valueToList).orThrow(AssertionError::new);
		List<?> expectedAsList = expectedValue.mapDefined(valueToList).orThrow(AssertionError::new);
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
			MaybeUndefined<Object> actualValueLine;
			if (actualLineExists)
			{
				actualNameLine = actualName + "[" + actualIndex + "]";
				actualValueLine = MaybeUndefined.defined(actualAsList.get(i));
				++actualIndex;
			}
			else
			{
				actualNameLine = actualName;
				actualValueLine = MaybeUndefined.undefined();
				elementsAreEqual = false;
			}

			boolean expectedLineExists = i < expectedSize;
			String expectedNameLine;
			MaybeUndefined<Object> expectedValueLine;
			if (expectedLineExists)
			{
				expectedNameLine = expectedName + "[" + expectedIndex + "]";
				expectedValueLine = MaybeUndefined.defined(expectedAsList.get(i));
				++expectedIndex;
			}
			else
			{
				expectedNameLine = expectedName;
				expectedValueLine = MaybeUndefined.undefined();
				elementsAreEqual = false;
			}
			ContextGenerator elementGenerator = new ContextGenerator(scope, configuration, actualNameLine,
				expectedNameLine);
			actualValueLine.ifDefined(elementGenerator::actualValue);
			expectedValueLine.ifDefined(elementGenerator::expectedValue);

			elementsAreEqual &= actualValueLine.equals(expectedValueLine);
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
		assert actualValue.isDefined() || expectedValue.isDefined() :
			"actualValue and expectedValue were both undefined";

		StringMappers stringMappers = configuration.stringMappers();
		String actualAsString = actualValue.mapDefined(stringMappers::toString).orDefault("");
		String expectedAsString = expectedValue.mapDefined(stringMappers::toString).orDefault("");
		DiffResult lines = diffGenerator.diff(actualAsString, expectedAsString);
		boolean diffLinesExist = !lines.getDiffLines().isEmpty();

		// When comparing multiline strings, this method is invoked one line at a time. If the actual or expected
		// value is undefined, it indicates that one of the values contains more lines than the other. The value
		// with fewer lines will be considered undefined on a per-line basis.
		int numberOfLines = Math.max(lines.getActualLines().size(), lines.getExpectedLines().size());
		// Don't diff boolean values
		if (!allowDiff || numberOfLines == 1 ||
			actualValue.mapDefined(v -> v instanceof Boolean).orDefault(false) ||
			expectedValue.mapDefined(v -> v instanceof Boolean).orDefault(false))
		{
			return getContextForSingleLine(lines);
		}
		int actualLineNumber = 0;
		int expectedLineNumber = 0;
		List<String> actualLines = lines.getActualLines();
		List<String> expectedLines = lines.getExpectedLines();
		List<Boolean> equalLines = lines.getEqualLines();

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
			actualAsString = actualValue.mapDefined(stringMappers::toString).orDefault("");
			expectedAsString = expectedValue.mapDefined(stringMappers::toString).orDefault("");
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

		List<MessageSection> context = new ArrayList<>();
		context.add(getDiffSection(actualName, actualAsString, diffLine, expectedName, expectedAsString));

		if (!actualValue.equals(expectedValue) && stringRepresentationsAreEqual(lines))
		{
			// If the String representation of the values is equal, output getClass(), hashCode(),
			// or System.identityHashCode() to figure out why they differ.
			List<MessageSection> optionalContext = compareTypes();
			if (!optionalContext.isEmpty())
			{
				context.add(new StringSection(""));
				context.addAll(optionalContext);
			}
		}
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
	 * @return the difference between the expected and actual values
	 * @throws AssertionError if {@code actualName} or {@code expectedName} are null
	 */
	private List<MessageSection> compareTypes()
	{
		assert actualValue.isDefined() || expectedValue.isDefined() :
			"actualValue and expectedValue were both undefined";
		Object actualValue = this.actualValue.orThrow(AssertionError::new);
		String actualClassName = getClassName(getClass(actualValue));

		Object expectedValue = this.expectedValue.orThrow(AssertionError::new);
		String expectedClassName = getClassName(getClass(expectedValue));
		if (!actualClassName.equals(expectedClassName))
		{
			return new ContextGenerator(scope, configuration, actualName + ".class", expectedName + ".class").
				actualValue(actualClassName).
				expectedValue(expectedClassName).
				allowDiff(false).
				build();
		}
		// Do not use config.toString() for hashCode values because their exact value does not matter, just the
		// fact that they are different.
		int actualHashCode = Objects.hashCode(actualValue);
		int expectedHashCode = Objects.hashCode(expectedValue);
		if (actualHashCode != expectedHashCode)
		{
			return new ContextGenerator(scope, configuration, actualName + ".hashCode",
				expectedName + ".hashCode").
				actualValue(actualHashCode).
				expectedValue(expectedHashCode).
				allowDiff(false).
				build();
		}
		int expectedIdentityHashCode = System.identityHashCode(expectedValue);
		int actualIdentityHashCode = System.identityHashCode(actualValue);
		if (actualIdentityHashCode != expectedIdentityHashCode)
		{
			return new ContextGenerator(scope, configuration, actualName + ".identityHashCode",
				expectedName + ".identityHashCode").
				actualValue(actualIdentityHashCode).
				expectedValue(expectedIdentityHashCode).
				allowDiff(false).
				build();
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
		actualValue.ifDefined(value -> result.append(", actualValue: ").append(value));
		result.append("expectedName: ").append(expectedName);
		expectedValue.ifDefined(value -> result.append(", expectedValue: ").append(value));
		result.append(", allowDiff: ").append(allowDiff);
		return result.toString();
	}
}