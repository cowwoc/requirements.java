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
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of OptionalVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpOptionalVerifier implements OptionalVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpOptionalVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public OptionalVerifier isPresent()
	{
		return this;
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(Object value, String name)
	{
		return null;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value, String name)
	{
		return this;
	}

	@Override
	public OptionalVerifier isIn(Collection<Optional<?>> collection)
	{
		return this;
	}

	@Override
	public OptionalVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public OptionalVerifier isNull()
	{
		return this;
	}

	@Override
	public OptionalVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public OptionalVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Optional<?>> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Optional<?> getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public OptionalVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
