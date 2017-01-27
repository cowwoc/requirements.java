/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.InetAddress;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of NoOpInetAddressVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpInetAddressVerifier implements InetAddressVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpInetAddressVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public InetAddressVerifier isEqualTo(InetAddress value)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isEqualTo(InetAddress value, String name)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isNotEqualTo(InetAddress value)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isNotEqualTo(InetAddress value, String name)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isIn(Collection<InetAddress> collection)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public InetAddressVerifier isNull()
	{
		return this;
	}

	@Override
	public InetAddressVerifier isNotNull()
	{
		return this;
	}

	@Override
	public InetAddressVerifier isIpV4()
	{
		return this;
	}

	@Override
	public InetAddressVerifier isIpV6()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public InetAddressVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<InetAddress> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public InetAddress getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public InetAddressVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
