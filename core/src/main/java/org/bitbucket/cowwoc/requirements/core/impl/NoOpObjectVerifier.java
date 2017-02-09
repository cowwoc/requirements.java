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
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ObjectVerifier that does nothing.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NoOpObjectVerifier<T> implements ObjectVerifier<T>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpObjectVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public ObjectVerifier<T> isEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotEqualTo(Object value)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotEqualTo(Object value, String name)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public ObjectVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public ObjectVerifier<T> asString(Consumer<StringVerifier> consumer)
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
