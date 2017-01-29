/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;
import org.bitbucket.cowwoc.requirements.core.diff.DiffResult;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link ObjectCapabilities}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class ObjectCapabilitiesImpl<S, T> implements ObjectCapabilities<S, T>
{
	/**
	 * @param o an object
	 * @return {@code null} if the object is null; otherwise, {@code o.getClass().getName()}
	 */
	private static String getNullableType(Object o)
	{
		if (o == null)
			return "null";
		return o.getClass().getName();
	}

	/**
	 * Converts a DiffResult into a list of key-value pairs.
	 *
	 * @param diff           a DiffResult
	 * @param actualToString the string representation of actual value
	 * @param actualName     the name of the actual value (e.g. "Actual" vs "Actual.class")
	 * @param expectedName   the name of the expected value (e.g. "Expected" vs "Expected.class")
	 * @return
	 */
	private static List<Entry<String, Object>> getContext(DiffResult diff, String actualToString,
		String actualName, String expectedName)
	{
		List<String> actual = diff.getActual();
		List<String> middle = diff.getMiddle();
		List<String> expected = diff.getExpected();
		int lines = actual.size();
		List<Entry<String, Object>> result = new ArrayList<>(2 * lines);
		if (!actualName.equals("Actual"))
		{
			// Include the string value even if it is equal
			result.add(new SimpleImmutableEntry<>("Actual", actualToString));
		}
		if (lines == 1)
		{
			result.add(new SimpleImmutableEntry<>(actualName, actual.get(0)));
			if (!middle.isEmpty())
				result.add(new SimpleImmutableEntry<>("Diff", middle.get(0)));
			result.add(new SimpleImmutableEntry<>(expectedName, expected.get(0)));
		}
		else
		{
			assert (expected.size() == lines): "lines: " + lines + ", expected.size(): " + expected.size();
			int actualLineNumber = 1;
			int expectedLineNumber = 1;
			// Indicates if the previous line was identical
			boolean skippedDupicates = false;
			for (int i = 0; i < lines; ++i)
			{
				String actualLine = actual.get(i);
				String expectedLine = expected.get(i);

				if (i != 0 && i != lines - 1 && actualLine.equals(expectedLine))
				{
					// Skip identical lines, unless they are the first or last line.
					skippedDupicates = true;
					++actualLineNumber;
					++expectedLineNumber;
					continue;
				}
				String actualNameForLine;
				if (actualLine.isEmpty())
					actualNameForLine = actualName;
				else
				{
					actualNameForLine = actualName + "@" + actualLineNumber;
					++actualLineNumber;
				}
				if (skippedDupicates)
				{
					skippedDupicates = false;
					skipDuplicateLines(result);
				}

				result.add(new SimpleImmutableEntry<>(actualNameForLine, actualLine));
				if (!middle.isEmpty())
					result.add(new SimpleImmutableEntry<>("Diff", middle.get(i)));
				String expectedNameForLine;
				if (expectedLine.isEmpty())
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
			if (skippedDupicates)
				skipDuplicateLines(result);
		}
		return result;
	}

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
	protected final ApplicationScope scope;
	protected final T actual;
	protected final String name;
	protected final Configuration config;

	/**
	 * Creates new ObjectCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ObjectCapabilitiesImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
	}

	/**
	 * @return this
	 */
	protected S getThis()
	{
		@SuppressWarnings("unchecked")
		S result = (S) this;
		return result;
	}

	@Override
	public S isEqualTo(T expected)
	{
		if (Objects.equals(actual, expected))
			return getThis();
		String actualName;
		String expectedName;
		String actualValue = Objects.toString(actual);
		String actualToString = actualValue;
		String expectedValue = Objects.toString(expected);
		if (actualValue.equals(expectedValue))
		{
			actualValue = getNullableType(actual);
			expectedValue = getNullableType(expected);
			if (actualValue.equals(expectedValue))
			{
				actualValue = String.valueOf(Objects.hashCode(actual));
				expectedValue = String.valueOf(Objects.hashCode(expected));
				actualName = "Actual.hashCode";
				expectedName = "Expected.hashCode";
			}
			else
			{
				actualName = "Actual.class";
				expectedName = "Expected.class";
			}
		}
		else
		{
			actualName = "Actual";
			expectedName = "Expected";
		}
		DiffResult result = scope.getDiffGenerator().diff(actualValue, expectedValue);
		List<Entry<String, Object>> context = getContext(result, actualToString, actualName,
			expectedName);
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s had an unexpected value.", name)).
			addContext(context).
			build();
	}

	@Override
	public S isEqualTo(T expected, String name)
	{
		scope.getInternalVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, expected))
			return getThis();
		String actualName;
		String expectedName;
		String actualValue = Objects.toString(actual);
		String actualToString = actualValue;
		String expectedValue = Objects.toString(expected);
		if (actualValue.equals(expectedValue))
		{
			actualValue = getNullableType(actual);
			expectedValue = getNullableType(expected);
			if (actualValue.equals(expectedValue))
			{
				actualValue = String.valueOf(Objects.hashCode(actual));
				expectedValue = String.valueOf(Objects.hashCode(expected));
				actualName = "Actual.hashCode";
				expectedName = "Expected.hashCode";
			}
			else
			{
				actualName = "Actual.class";
				expectedName = "Expected.class";
			}
		}
		else
		{
			actualName = "Actual";
			expectedName = "Expected";
		}
		DiffResult result = scope.getDiffGenerator().diff(actualValue, expectedValue);
		List<Entry<String, Object>> context = getContext(result, actualToString, actualName,
			expectedName);
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be equal to %s.", this.name, name)).
			addContext(context).
			build();
	}

	@Override
	public S isNotEqualTo(T value)
	{
		if (!Objects.equals(actual, value))
			return getThis();

		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be equal to %s.", name, value)).
			build();
	}

	@Override
	public S isNotEqualTo(T value, String name)
	{
		scope.getInternalVerifier().requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, value))
			return getThis();

		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be equal to %s.", this.name, name)).
			addContext("Actual", value).
			build();
	}

	@Override
	public S isIn(Collection<T> collection)
	{
		scope.getInternalVerifier().requireThat(collection, "collection").isNotNull();
		if (collection.contains(actual))
			return getThis();

		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat(type, "type").isNotNull();
		if (type.isInstance(actual))
			return getThis();

		Class<?> actualClass;
		if (actual == null)
			actualClass = null;
		else
			actualClass = actual.getClass();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be an instance of %s.", name, type)).
			addContext("Actual", actualClass).
			build();
	}

	@Override
	public S isNull()
	{
		if (actual == null)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be null.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNull()
	{
		if (actual != null)
			return getThis();
		throw new ExceptionBuilder(config, NullPointerException.class,
			String.format("%s may not be null", name)).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return getThis();
	}

	@Override
	public Optional<T> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public T getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public S configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return getThis();
	}
}
