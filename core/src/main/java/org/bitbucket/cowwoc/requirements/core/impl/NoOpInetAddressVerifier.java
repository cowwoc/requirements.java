/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.InetAddress;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;

/**
 * An implementation of {@code InetAddressVerifier} that does nothing.
 *
 * @author Gili Tzabari
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
