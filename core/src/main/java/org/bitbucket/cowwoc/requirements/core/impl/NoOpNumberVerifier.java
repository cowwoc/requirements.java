/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of NumberVerifier that does nothing.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NoOpNumberVerifier<T extends Number & Comparable<? super T>>
	implements NumberVerifier<T>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpNumberVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public NumberVerifier<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isBetween(T min, T max)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNegative()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotNegative()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotPositive()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotZero()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isPositive()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isZero()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public NumberVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public NumberVerifier<T> asString(Consumer<StringVerifier> consumer)
	{
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
}