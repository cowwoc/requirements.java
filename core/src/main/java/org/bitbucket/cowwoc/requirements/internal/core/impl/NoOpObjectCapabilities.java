/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

/**
 * Extendable implementation of {@link ObjectCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class NoOpObjectCapabilities<S, T> implements ObjectCapabilities<S, T>
{
	protected final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpObjectCapabilities(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	/**
	 * @return this
	 */
	protected abstract S getThis();

	@Override
	public S isEqualTo(Object value)
	{
		return getThis();
	}

	@Override
	public S isEqualTo(Object value, String name)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value)
	{
		return getThis();
	}

	@Override
	public S isNotEqualTo(Object value, String name)
	{
		return getThis();
	}

	@Override
	public S isIn(Collection<T> collection)
	{
		return getThis();
	}

	@Override
	public S isNotIn(Collection<T> collection)
	{
		return getThis();
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		return getThis();
	}

	@Override
	public S isNull()
	{
		return getThis();
	}

	@Override
	public S isNotNull()
	{
		return getThis();
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public S asString(Consumer<StringVerifier> consumer)
	{
		return getThis();
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
