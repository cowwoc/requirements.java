/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of NoOpInetAddressVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpInetAddressVerifier implements InetAddressVerifier
{
	INSTANCE;

	@Override
	public InetAddressVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public InetAddressVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public InetAddressVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
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
		return NoOpStringVerifier.INSTANCE;
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
	public InetAddressVerifier isolate(Consumer<InetAddressVerifier> consumer)
	{
		return this;
	}
}
