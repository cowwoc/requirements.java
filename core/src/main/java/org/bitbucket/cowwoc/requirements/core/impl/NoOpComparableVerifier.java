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
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ComparableVerifier that does nothing.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NoOpComparableVerifier<T extends Comparable<? super T>>
	implements ComparableVerifier<T>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpComparableVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isBetween(T min, T max)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public ComparableVerifier<T> asString(Consumer<StringVerifier> consumer)
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

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public ComparableVerifier<T> configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
