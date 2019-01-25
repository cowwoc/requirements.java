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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Default implementation of {@link UrlVerifier}.
 */
public final class UrlVerifierImpl extends ObjectCapabilitiesImpl<UrlVerifier, URL>
	implements UrlVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected UrlVerifierImpl(ApplicationScope scope, String name, URL actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public UriVerifier asUri()
	{
		try
		{
			URI uri = actual.toURI();
			return new UriVerifierImpl(scope, name, uri, config);
		}
		catch (URISyntaxException e)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				name + " is not a valid URI", e).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public UrlVerifier asUri(Consumer<UriVerifier> consumer)
	{
		consumer.accept(asUri());
		return this;
	}

}
