/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
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
	 * <h3>Syntax</h3>
	 * <pre>
	 * Actual  : &lt;&nbsp;&nbsp;&nbsp;&nbsp;&gt;
	 * Expected: [text]
	 * </pre>
	 * The rectangular brackets denote text that is present in one string but not the other.<br>
	 * The angle brackets indicate the corresponding position in the other string. This zero-width placeholder does not contribute any characters to the string it is found in. If this confuses you, read on for more concrete examples.
	 * <h3>Example 1</h3>
	 * <pre>
	 * Actual   = "Foo"
	 * Expected = "Bar"
	 * </pre>
	 * results in the following diff:
	 * <pre>
	 * Actual  : [Foo]&lt;   &gt;
	 * Expected: &lt;   &gt;[Bar]
	 * </pre>
	 * Meaning, to go from {@code Actual} to {@code Expected} we need to delete "Foo" and insert "Bar".
	 * <h3>Example 2</h3>
	 * <pre>
	 * Actual   = "Foo"
	 * Expected = "   Foo"
	 * </pre>
	 * results in the following diff:
	 * <pre>
	 * Actual  : &lt;   &gt;Foo
	 * Expected: [   ]Foo
	 * </pre>
	 * Meaning, to go from {@code Actual} to {@code Expected} we need to insert three spaces to the beginning of {@code Actual}.
	 *
	 * @param expected the expected value
	 * @param actual   the actual value
	 */
	private static void diff(StringBuilder expected, StringBuilder actual)
	{
		DiffMatchPatch diffFactory = new DiffMatchPatch();
		LinkedList<Diff> components = diffFactory.diffMain(actual.toString(), expected.toString());
		diffFactory.diffCleanupSemantic(components);
		int expectedOffset = 0;
		int actualOffset = 0;
		for (Diff component: components)
		{
			switch (component.operation)
			{
				case EQUAL:
				{
					expectedOffset += component.text.length();
					actualOffset += component.text.length();
					break;
				}
				case INSERT:
				{
					expected.insert(expectedOffset, "[");

					// Skip '[' + text
					expectedOffset += 1 + component.text.length();

					expected.insert(expectedOffset, "]");

					// Skip ']'
					++expectedOffset;

					actual.insert(actualOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + spaces + '>'
					actualOffset += "<".length() + component.text.length() + ">".length();
					break;
				}
				case DELETE:
				{
					actual.insert(actualOffset, "[");

					// Skip '[' + text
					actualOffset += 1 + component.text.length();

					actual.insert(actualOffset, "]");

					// Skip ']'
					++actualOffset;

					expected.insert(expectedOffset, "<" + Strings.repeat(" ", component.text.length()) + ">");

					// Skip '<' + spaces + '>'
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
	public ObjectRequirements<T> withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
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
			"Actual", actual,
			"Expected", expected);
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
			"Actual", actual,
			"Expected", expected);
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
	public ObjectRequirements<T> isIn(Collection<T> collection)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(collection, "collection").isNotNull();
		if (collection.contains(parameter))
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection),
			"Actual", parameter);
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
