/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ClassVerifier that does nothing.
 *
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public final class NoOpClassVerifier<T> implements ClassVerifier<T>
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpClassVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isEqualTo(Class<T> value)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isEqualTo(Class<T> value, String name)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isNotEqualTo(Class<T> value)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isNotEqualTo(Class<T> value, String name)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isIn(Collection<Class<T>> collection)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public ClassVerifier<T> asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Class<T>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Class<T> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public ClassVerifier<T> configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
