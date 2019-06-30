/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Default implementation of {@code UriVerifier}.
 */
public final class UriVerifierImpl extends ObjectCapabilitiesImpl<UriVerifier, URI>
	implements UriVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	public UriVerifierImpl(ApplicationScope scope, String name, URI actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public UriVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be absolute.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public UrlVerifier asUrl()
	{
		try
		{
			URL url = actual.toURL();
			return new UrlVerifierImpl(scope, name, url, config);
		}
		catch (MalformedURLException | IllegalArgumentException e)
		{
			throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public UriVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUrl());
		return this;
	}
}
