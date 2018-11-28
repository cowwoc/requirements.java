/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.internal.core.util.Objects;

/**
 * Extendable implementation of {@link ObjectCapabilities}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class ObjectCapabilitiesImpl<S, T> implements ObjectCapabilities<S, T>
{
	protected final ApplicationScope scope;
	protected final String name;
	protected final T actual;
	protected final Configuration config;

	/**
	 * Creates new ObjectCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ObjectCapabilitiesImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.name = name;
		this.actual = actual;
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
	public S isEqualTo(Object expected)
	{
		if (Objects.equals(actual, expected))
			return getThis();

		DiffToContextGenerator contextGenerator = new DiffToContextGenerator(config,
			scope.getDiffGenerator());
		List<Entry<String, Object>> context = contextGenerator.getContext("Actual", actual, "Expected",
			expected);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s had an unexpected value.", name)).
			addContext(context).
			build();
	}

	@Override
	public S isEqualTo(String name, Object expected)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, expected))
			return getThis();

		DiffToContextGenerator contextGenerator = new DiffToContextGenerator(config,
			scope.getDiffGenerator());
		List<Entry<String, Object>> context = contextGenerator.getContext("Actual", actual, "Expected",
			expected);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be equal to %s.", this.name, name)).
			addContext(context).
			build();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		if (!Objects.equals(actual, other))
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be equal to %s", name, config.toString(other))).
			build();
	}

	@Override
	public S isNotEqualTo(String name, Object other)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, other))
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be equal to %s.", this.name, name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isSameObjectAs(String name, Object expected)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (actual == expected)
			return getThis();

		DiffToContextGenerator contextGenerator = new DiffToContextGenerator(config,
			scope.getDiffGenerator());
		List<Entry<String, Object>> context = contextGenerator.getContext("Actual", actual, "Expected",
			expected);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be the same object as %s.", this.name, name)).
			addContext(context).
			build();
	}

	@Override
	public S isNotSameObjectAs(String name, Object other)
	{
		scope.getInternalVerifier().requireThat("name", name).isNotNull().trim().isNotEmpty();
		if (actual != other)
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be the same object as %s.", this.name, name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isIn(Collection<? super T> collection)
	{
		scope.getInternalVerifier().requireThat("collection", collection).isNotNull();
		if (collection.contains(actual))
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be one of %s.", this.name, collection)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotIn(Collection<? super T> collection)
	{
		// Use-case: "actual" may not be equal to one or more reserved values
		scope.getInternalVerifier().requireThat("collection", collection).isNotNull();
		if (!collection.contains(actual))
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be in %s.", this.name, collection)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat("type", type).isNotNull();
		if (type.isInstance(actual))
			return getThis();

		String actualClass;
		if (actual == null)
			actualClass = "null";
		else
			actualClass = actual.getClass().getName();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be an instance of %s.", name, type.getName())).
			addContext("Actual.getClass()", actualClass).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat("type", type).isNotNull();
		if (!type.isInstance(actual))
			return getThis();

		String actualClass;
		if (actual == null)
			actualClass = "null";
		else
			actualClass = actual.getClass().getName();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be an instance of %s.", name, type.getName())).
			addContext("Actual.getClass()", actualClass).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNull()
	{
		if (actual == null)
			return getThis();

		// Output a diff because actual.toString() may return "null" which is misleading
		DiffToContextGenerator contextGenerator = new DiffToContextGenerator(config,
			scope.getDiffGenerator());
		List<Entry<String, Object>> context = contextGenerator.getContext("Actual", actual, "Expected",
			null);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be null.", name)).
			addContext(context).
			build();
	}

	@Override
	public S isNotNull()
	{
		if (actual != null)
			return getThis();

		throw new ExceptionBuilder(scope, config, NullPointerException.class,
			String.format("%s may not be null", name)).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, config.toString(actual), name, config);
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
}
