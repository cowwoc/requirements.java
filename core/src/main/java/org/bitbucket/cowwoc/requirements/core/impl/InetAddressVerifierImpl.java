/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of InetAddressVerifier.
 *
 * @author Gili Tzabari
 */
public final class InetAddressVerifierImpl
	extends ObjectCapabilitiesImpl<InetAddressVerifier, InetAddress>
	implements InetAddressVerifier
{
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
		super(scope, actual, name, config);
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
}
