/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;

import java.net.InetAddress;

/**
 * An implementation of {@link InetAddressVerifier} that does nothing.
 */
public final class NoOpInetAddressVerifier
	extends NoOpObjectCapabilities<InetAddressVerifier, InetAddress>
	implements InetAddressVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpInetAddressVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected InetAddressVerifier getThis()
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
}
