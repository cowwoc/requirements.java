/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.UrlVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link UriVerifier}.
 *
 * @author Gili Tzabari
 */
public final class UriVerifierImpl extends ObjectCapabilitiesImpl<UriVerifier, URI>
	implements UriVerifier
{
	/**
	 * Creates new UriVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public UriVerifierImpl(ApplicationScope scope, URI actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public UriVerifier isAbsolute()
	{
		if (actual.isAbsolute())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be absolute.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public UrlVerifier asUrl()
	{
		try
		{
			URL url = actual.toURL();
			return new UrlVerifierImpl(scope, url, name, config);
		}
		catch (MalformedURLException | IllegalArgumentException e)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s is not a valid URL", name), e).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public UriVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		consumer.accept(asUrl());
		return this;
	}
}
