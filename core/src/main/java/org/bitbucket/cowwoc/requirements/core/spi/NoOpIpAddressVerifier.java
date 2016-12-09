/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.net.InetAddress;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.IpAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of NoOpIpAddressVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpIpAddressVerifier implements IpAddressVerifier
{
	INSTANCE;

	@Override
	public IpAddressVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public IpAddressVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public IpAddressVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isEqualTo(InetAddress value)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isEqualTo(InetAddress value, String name)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isNotEqualTo(InetAddress value)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isNotEqualTo(InetAddress value, String name)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isIn(Collection<InetAddress> collection)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public IpAddressVerifier isNull()
	{
		return this;
	}

	@Override
	public IpAddressVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public IpAddressVerifier isolate(Consumer<IpAddressVerifier> consumer)
	{
		return this;
	}
}
