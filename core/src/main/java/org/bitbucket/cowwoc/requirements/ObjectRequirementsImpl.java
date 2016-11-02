/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.diff.string.DiffResult;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of ObjectRequirements.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class ObjectRequirementsImpl<T> implements ObjectRequirements<T>
{
	private final SingletonScope scope;
	private final T parameter;
	private final String name;
	private final Configuration config;

	/**
	 * Creates new ObjectRequirementsImpl.
	 *
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	ObjectRequirementsImpl(SingletonScope scope, T parameter, String name, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
	}

	@Override
	public ObjectRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ObjectRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ObjectRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ObjectRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> isNull()
	{
		if (parameter == null)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be null.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public ObjectRequirements<T> isNotNull()
	{
		if (parameter != null)
			return this;
		throw config.exceptionBuilder(NullPointerException.class,
			String.format("%s may not be null", name)).
			build();
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value)
	{
		if (Objects.equals(parameter, value))
			return this;
		DiffResult result = scope.getDiffGenerator().diff(parameter.toString(), value.toString());
		List<Entry<String, Object>> context = getContext(result);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s had an unexpected value.", name)).
			addContext(context).
			build();
	}

	private List<Entry<String, Object>> getContext(DiffResult diff)
	{
		List<String> actual = diff.getActual();
		List<String> middle = diff.getMiddle();
		List<String> expected = diff.getExpected();
		int lines = actual.size();
		List<Entry<String, Object>> result = new ArrayList<>(2 * lines);
		if (lines == 1)
		{
			result.add(new SimpleImmutableEntry<>("Actual", actual.get(0)));
			if (!middle.isEmpty())
				result.add(new SimpleImmutableEntry<>("Diff", middle.get(0)));
			result.add(new SimpleImmutableEntry<>("Expected", expected.get(0)));
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
				String actualName;
				if (actualLine.isEmpty())
					actualName = "Actual";
				else
				{
					actualName = "Actual@" + actualLineNumber;
					++actualLineNumber;
				}
				if (skippedDupicates)
				{
					skippedDupicates = false;
					skipDuplicateLines(result);
				}

				result.add(new SimpleImmutableEntry<>(actualName, actualLine));
				if (!middle.isEmpty())
					result.add(new SimpleImmutableEntry<>("Diff", middle.get(i)));
				String expectedName;
				if (expectedLine.isEmpty())
					expectedName = "Expected";
				else
				{
					expectedName = "Expected@" + expectedLineNumber;
					++expectedLineNumber;
				}
				if (i < lines - 1)
					expectedLine += "\n";
				result.add(new SimpleImmutableEntry<>(expectedName, expectedLine));
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
	private void skipDuplicateLines(List<Entry<String, Object>> entries)
	{
		Entry<String, Object> lastEntry = entries.get(entries.size() - 1);
		String newValue = lastEntry.getValue() + "\n[...]\n";
		entries.set(entries.size() - 1, new SimpleImmutableEntry<>(lastEntry.getKey(), newValue));
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value, String name)
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		DiffResult result = scope.getDiffGenerator().diff(parameter.toString(), value.toString());
		List<Entry<String, Object>> context = getContext(result);

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be equal to %s.", this.name, name)).
			addContext(context).
			build();
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value)
	{
		if (!Objects.equals(parameter, value))
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not be equal to %s", name, value)).
			build();
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value, String name)
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not be equal to %s.", this.name, name)).
			addContext("Actual", value).
			build();
	}

	@Override
	public ObjectRequirements<T> isIn(Collection<T> collection)
	{
		Requirements.requireThat(collection, "collection").isNotNull();
		if (collection.contains(parameter))
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public ObjectRequirements<T> isInstanceOf(Class<?> type)
	{
		Requirements.requireThat(type, "type").isNotNull();
		if (type.isInstance(parameter))
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be an instance of %s.", name, type)).
			addContext("Actual: %s", parameter.getClass()).
			build();
	}

	@Override
	public ObjectRequirements<T> isolate(Consumer<ObjectRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
