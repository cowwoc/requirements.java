/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;

import java.net.URL;
import java.util.function.Consumer;

/**
 * A {@code UrlVerifier} that does nothing.
 */
public final class NoOpUrlVerifier
	extends NoOpObjectCapabilities<UrlVerifier, URL>
	implements UrlVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpUrlVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected UrlVerifier getThis()
	{
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return new NoOpUriVerifier(config);
	}

	@Override
	public UrlVerifier asUri(Consumer<UriVerifier> consumer)
	{
		return this;
	}
}
