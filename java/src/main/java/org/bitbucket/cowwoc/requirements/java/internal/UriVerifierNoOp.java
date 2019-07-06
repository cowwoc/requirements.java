/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.net.URI;
import java.util.function.Consumer;

/**
 * A {@code UriVerifier} that does nothing.
 */
public final class UriVerifierNoOp
	extends AbstractObjectVerifierNoOp<UriVerifier, URI>
	implements UriVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public UriVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected UriVerifier getThis()
	{
		return this;
	}

	@Override
	public UriVerifier isAbsolute()
	{
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		return new UrlVerifierNoOp(config);
	}

	@Override
	public UriVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
