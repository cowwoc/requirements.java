/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.net.URL;
import java.util.function.Consumer;

/**
 * A {@code UrlVerifier} that does nothing.
 */
public final class UrlVerifierNoOp
	extends AbstractObjectVerifierNoOp<UrlVerifier, URL>
	implements UrlVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public UrlVerifierNoOp(Configuration config)
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
		return new UriVerifierNoOp(config);
	}

	@Override
	public UrlVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
