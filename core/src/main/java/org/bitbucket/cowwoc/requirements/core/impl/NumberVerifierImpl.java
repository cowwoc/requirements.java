/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@code NumberVerifier}.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NumberVerifierImpl<T extends Number & Comparable<? super T>>
	implements NumberVerifier<T>
{
	private final ApplicationScope scope;
	private final T actual;
	private final String name;
	private final Configuration config;
	private final ComparableVerifier<T> asComparable;

	/**
	 * Creates a new NumberVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public NumberVerifierImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asComparable = new ComparableVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public NumberVerifier<T> isEqualTo(T value)
	{
		asComparable.isEqualTo(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isEqualTo(T value, String name)
	{
		asComparable.isEqualTo(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isNotEqualTo(T value)
	{
		asComparable.isNotEqualTo(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isNotEqualTo(T value, String name)
	{
		asComparable.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isIn(Collection<T> collection)
	{
		asComparable.isIn(collection);
		return this;
	}

	@Override
	public NumberVerifier<T> isInstanceOf(Class<?> type)
	{
		asComparable.isInstanceOf(type);
		return this;
	}

	@Override
	public NumberVerifier<T> isNull()
	{
		asComparable.isNull();
		return this;
	}

	@Override
	public NumberVerifier<T> isNotNull()
	{
		asComparable.isNotNull();
		return this;
	}

	@Override
	public NumberVerifier<T> isNegative()
	{
		if (actual.longValue() < 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberVerifier<T> isNotNegative()
	{
		if (actual.longValue() >= 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberVerifier<T> isZero()
	{
		if (actual.longValue() == 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be zero.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberVerifier<T> isNotZero()
	{
		if (actual.longValue() != 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public NumberVerifier<T> isPositive()
	{
		if (actual.longValue() > 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberVerifier<T> isNotPositive()
	{
		if (actual.longValue() <= 0L)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberVerifier<T> isGreaterThan(T value, String name)
	{
		asComparable.isGreaterThan(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThan(T value)
	{
		asComparable.isGreaterThan(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThanOrEqualTo(T value, String name)
	{
		asComparable.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThanOrEqualTo(T value)
	{
		asComparable.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThan(T value, String name)
	{
		asComparable.isLessThan(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThan(T value)
	{
		asComparable.isLessThan(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThanOrEqualTo(T value, String name)
	{
		asComparable.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThanOrEqualTo(T value)
	{
		asComparable.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberVerifier<T> isBetween(T min, T max)
	{
		asComparable.isBetween(min, max);
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public NumberVerifier<T> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<T> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public T getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public NumberVerifier<T> configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
