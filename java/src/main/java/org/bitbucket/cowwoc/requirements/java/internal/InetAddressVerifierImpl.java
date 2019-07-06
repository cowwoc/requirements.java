/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.InetAddressValidator;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.StringValidator;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.net.InetAddress;

/**
 * Default implementation of {@code InetAddressVerifier}.
 */
public final class InetAddressVerifierImpl
	extends AbstractObjectVerifier<InetAddressVerifier, InetAddressValidator, InetAddress>
	implements InetAddressVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public InetAddressVerifierImpl(InetAddressValidator validator)
	{
		super(validator);
	}

	@Override
	protected InetAddressVerifier getThis()
	{
		return this;
	}

	@Override
	public InetAddressVerifier isIpV4()
	{
		validator = validator.isIpV4();
		return validationResult();
	}

	@Override
	public InetAddressVerifier isIpV6()
	{
		validator = validator.isIpV6();
		return validationResult();
	}

	@Override
	public StringVerifier asString()
	{
		StringValidator newValidator = validator.asString();
		return validationResult(() -> new StringVerifierImpl(newValidator));
	}
}
