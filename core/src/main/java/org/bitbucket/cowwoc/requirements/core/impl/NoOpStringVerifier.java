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
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.ext.StringBasedExtension;

/**
 * An implementation of StringVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpStringVerifier implements StringVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpStringVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		return this;
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		return new NoOpInetAddressVerifier(config);
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return new NoOpUriVerifier(config);
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier isEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		return this;
	}

	@Override
	public StringVerifier trim()
	{
		return this;
	}

	@Override
	public StringVerifier isEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringVerifier isEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value)
	{
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public StringVerifier isIn(Collection<String> collection)
	{
		return this;
	}

	@Override
	public StringVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public StringVerifier isNull()
	{
		return this;
	}

	@Override
	public StringVerifier isNotNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return new NoOpContainerSizeVerifier(config);
	}

	@Override
	public StringBasedExtension<StringVerifier, String> length(
		Consumer<ContainerSizeVerifier> consumer)
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return this;
	}

	@Override
	public StringVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<String> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public String getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public StringVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
