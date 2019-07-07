/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

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
	private static final UrlVerifierNoOp INSTANCE = new UrlVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static UrlVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private UrlVerifierNoOp()
	{
	}

	@Override
	protected UrlVerifier getThis()
	{
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		return UriVerifierNoOp.getInstance();
	}

	@Override
	public UrlVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
