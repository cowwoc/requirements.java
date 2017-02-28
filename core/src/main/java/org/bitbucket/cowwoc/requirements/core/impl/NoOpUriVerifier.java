/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.URI;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;

/**
 * An implementation of {@code UriVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpUriVerifier
	extends NoOpObjectCapabilities<UriVerifier, URI>
	implements UriVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpUriVerifier(Configuration config)
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
}
