/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;

/**
 * An implementation of UriVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpUriVerifier implements UriVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpUriVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public UriVerifier isAbsolute()
	{
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriVerifier isEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value)
	{
		return this;
	}

	@Override
	public UriVerifier isNotEqualTo(URI value, String name)
	{
		return this;
	}

	@Override
	public UriVerifier isIn(Collection<URI> collection)
	{
		return this;
	}

	@Override
	public UriVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public UriVerifier isNull()
	{
		return this;
	}

	@Override
	public UriVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public UriVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<URI> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public URI getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public UriVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
