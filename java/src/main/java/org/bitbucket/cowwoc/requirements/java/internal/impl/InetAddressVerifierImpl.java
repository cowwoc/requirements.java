/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;

/**
 * Default implementation of InetAddressVerifier.
 */
public final class InetAddressVerifierImpl
	extends ObjectCapabilitiesImpl<InetAddressVerifier, InetAddress>
	implements InetAddressVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	protected InetAddressVerifierImpl(ApplicationScope scope, String name, InetAddress actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public InetAddressVerifier isIpV4()
	{
		if (actual instanceof Inet4Address)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be an IP v4 address.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public InetAddressVerifier isIpV6()
	{
		if (actual instanceof Inet6Address)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
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
}
