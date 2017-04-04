/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.UrlVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link UrlVerifier}.
 *
 * @author Gili Tzabari
 */
public final class UrlVerifierImpl extends ObjectCapabilitiesImpl<UrlVerifier, URL>
	implements UrlVerifier
{
	/**
	 * Creates new UrlVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public UrlVerifierImpl(ApplicationScope scope, URL actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public UriVerifier asUri()
	{
		try
		{
			URI uri = actual.toURI();
			return new UriVerifierImpl(scope, uri, name, config);
		}
		catch (URISyntaxException e)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s is not a valid URI", name), e).
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
