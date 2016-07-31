/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.DELETE;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.EQUAL;
import static org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Operation.INSERT;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of ObjectRequirements.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class ObjectRequirementsImpl<T> implements ObjectRequirements<T>
{
	/**
	 * Calculates the difference between two strings.
	 * <p>
	 * @param expected the expected value
	 * @param actual   the actual value
	 */
	private static void diff(StringBuilder expected, StringBuilder actual)
	{
		DiffMatchPatch diffFactory = new DiffMatchPatch();
		LinkedList<Diff> diff = diffFactory.diffMain(expected.toString(), actual.toString());
		diffFactory.diffCleanupSemantic(diff);
		int expectedOffset = 0;
		int actualOffset = 0;
		for (Diff component: diff)
		{
			switch (component.operation)
			{
				case EQUAL:
				{
					expectedOffset += component.text.length();
					actualOffset += component.text.length();
					break;
				}
				case DELETE:
				{
					expected.insert(expectedOffset, "<");

					// Skip '<' + text
					expectedOffset += 1 + component.text.length();

					expected.insert(expectedOffset, ">");

					// Skip '>'
					++expectedOffset;

					actual.insert(actualOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + text + '>'
					actualOffset += "<".length() + component.text.length() + ">".length();
					break;
				}
				case INSERT:
				{
					actual.insert(actualOffset, "<");

					// Skip '<' + text
					actualOffset += 1 + component.text.length();

					actual.insert(actualOffset, ">");

					// Skip '>'
					++actualOffset;

					expected.insert(expectedOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + text + '>'
					expectedOffset += "<".length() + component.text.length() + ">".length();
					break;
				}
				default:
					throw new AssertionError(component.operation.name());
			}
		}
	}
	private final T parameter;
	private final String name;
	private final Configuration config;

	/**
	 * Creates new ObjectRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ObjectRequirementsImpl(T parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
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
		return new ObjectRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ObjectRequirements<T> isNull() throws IllegalArgumentException
	{
		if (parameter == null)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be null.", name),
			"Actual", parameter);
	}

	@Override
	public ObjectRequirements<T> isNotNull() throws NullPointerException
	{
		if (parameter != null)
			return this;
		throw config.createException(NullPointerException.class,
			String.format("%s may not be null", name));
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value) throws IllegalArgumentException
	{
		if (Objects.equals(parameter, value))
			return this;
		StringBuilder expected = new StringBuilder(value.toString());
		StringBuilder actual = new StringBuilder(parameter.toString());
		diff(expected, actual);

		throw config.createException(IllegalArgumentException.class,
			String.format("%s had an unexpected value.", name),
			"Expected", expected,
			"Actual", actual);
	}

	@Override
	public ObjectRequirements<T> isEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		StringBuilder expected = new StringBuilder(value.toString());
		StringBuilder actual = new StringBuilder(parameter.toString());
		diff(expected, actual);

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be equal to %s.", this.name, name),
			"Expected", expected,
			"Actual", actual);
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value) throws IllegalArgumentException
	{
		if (!Objects.equals(parameter, value))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s", name, value));
	}

	@Override
	public ObjectRequirements<T> isNotEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not be equal to %s.", this.name, name),
			"Actual", value);
	}

	@Override
	public ObjectRequirements<T> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(type, "type").isNotNull();
		if (type.isInstance(parameter))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be an instance of %s.", name, type),
			"Actual: %s", parameter.getClass());
	}

	@Override
	public ObjectRequirements<T> isolate(Consumer<ObjectRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
