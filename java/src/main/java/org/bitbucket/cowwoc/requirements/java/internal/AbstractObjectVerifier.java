/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Objects;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Default implementation of {@code ExtensibleObjectVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractObjectVerifier<S, T> implements ExtensibleObjectVerifier<S, T>
{
	protected final ApplicationScope scope;
	protected final String name;
	protected final T actual;
	protected final Configuration config;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractObjectVerifier(ApplicationScope scope, String name, T actual, Configuration config)
	{
		assert (scope != null) : "scope may not be null";
		assert (name != null) : "name may not be null";
		assert (!name.isEmpty()) : "name may not be empty";
		assert (config != null) : "config may not be null";
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

		List<Entry<String, Object>> context = getContext(expected);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " had an unexpected value.").
			addContext(context).
			build();
	}

	/**
	 * @param expected the expected value
	 * @return the list of name-value pairs to append to the exception message
	 */
	private List<Entry<String, Object>> getContext(Object expected)
	{
		ContextGenerator contextGenerator = new ContextGenerator(config, scope.getDiffGenerator());
		return contextGenerator.getContext("Actual", actual, "Expected", expected);
	}

	@Override
	public S isEqualTo(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(actual, expected))
			return getThis();

		List<Entry<String, Object>> context = getContext(expected);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " must be equal to " + name + ".").
			addContext(context).
			build();
	}

	@Override
	public S isNotEqualTo(Object other)
	{
		if (!Objects.equals(actual, other))
			return getThis();

		String value = config.toString(other);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be equal to " + value).
			build();
	}

	@Override
	public S isNotEqualTo(Object other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(actual, other))
			return getThis();

		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " may not be equal to " + name + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isSameObjectAs(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual == expected)
			return getThis();

		List<Entry<String, Object>> context = getContext(expected);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " must be the same object as " + name + ".").
			addContext(context).
			build();
	}

	@Override
	public S isNotSameObjectAs(Object other, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (actual != other)
			return getThis();

		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " may not be the same object as " + name + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();
		if (collection.contains(actual))
			return getThis();

		String collectionAsString = config.toString(collection);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " must be one of " + collectionAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotOneOf(Collection<? super T> collection)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(collection, "collection").isNotNull();
		if (!collection.contains(actual))
			return getThis();

		String collectionAsString = config.toString(collection);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " may not be one of " + collectionAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (type.isInstance(actual))
			return getThis();

		String actualClass;
		if (actual == null)
			actualClass = "null";
		else
			actualClass = actual.getClass().getName();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be an instance of " + type.getName() + ".").
			addContext("Actual.getClass()", actualClass).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotInstanceOf(Class<?> type)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(type, "type").isNotNull();
		if (!type.isInstance(actual))
			return getThis();

		String actualClass = actual.getClass().getName();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be an instance of " + type.getName() + ".").
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
		List<Entry<String, Object>> context = getContext(null);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be null.").
			addContext(context).
			build();
	}

	@Override
	public S isNotNull()
	{
		if (actual != null)
			return getThis();

		throw new ExceptionBuilder<>(scope, config, NullPointerException.class,
			name + " may not be null").
			build();
	}

	@Override
	public StringVerifier asString()
	{
		String value = config.toString(actual);
		return new StringVerifierImpl(scope, name, value, config);
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asString());
		return getThis();
	}

	@Override
	public Optional<T> getActual()
	{
		return Optional.ofNullable(actual);
	}
}
