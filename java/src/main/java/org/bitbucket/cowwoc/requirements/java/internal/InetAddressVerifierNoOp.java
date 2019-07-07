/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.net.InetAddress;

/**
 * An {@code InetAddressVerifier} that does nothing.
 */
public final class InetAddressVerifierNoOp
	extends AbstractObjectVerifierNoOp<InetAddressVerifier, InetAddress>
	implements InetAddressVerifier
{
	private static final InetAddressVerifierNoOp INSTANCE = new InetAddressVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static InetAddressVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private InetAddressVerifierNoOp()
	{
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
