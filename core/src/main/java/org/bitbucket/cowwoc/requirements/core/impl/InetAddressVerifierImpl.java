/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of InetAddressVerifier.
 *
 * @author Gili Tzabari
 */
public final class InetAddressVerifierImpl implements InetAddressVerifier
{
	private final ApplicationScope scope;
	private final InetAddress actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<InetAddress> asObject;

	/**
	 * Creates new InetAddressVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public InetAddressVerifierImpl(ApplicationScope scope, InetAddress actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public InetAddressVerifier isEqualTo(InetAddress value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public InetAddressVerifier isEqualTo(InetAddress value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public InetAddressVerifier isNotEqualTo(InetAddress value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public InetAddressVerifier isNotEqualTo(InetAddress value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public InetAddressVerifier isIn(Collection<InetAddress> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public InetAddressVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public InetAddressVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public InetAddressVerifier isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public InetAddressVerifier isIpV4()
	{
		if (actual instanceof Inet4Address)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be an IP v4 address.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public InetAddressVerifier isIpV6()
	{
		if (actual instanceof Inet6Address)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be an IP v6 address.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		// InetAddress.toString() returns hostname/ip-address, but this cannot be fed back into
		// InetAddress.getByName(String). Instead, we use InetAddress.getHostName() which can.
		String hostName = actual.getHostName();
		return new StringVerifierImpl(scope, hostName, name, config);
	}

	@Override
	public InetAddressVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<InetAddress> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public InetAddress getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public InetAddressVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
