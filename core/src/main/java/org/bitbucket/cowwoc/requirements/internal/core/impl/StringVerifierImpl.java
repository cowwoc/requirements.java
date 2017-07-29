/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.UrlVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link StringVerifier}.
 *
 * @author Gili Tzabari
 */
public final class StringVerifierImpl extends ObjectCapabilitiesImpl<StringVerifier, String>
	implements StringVerifier
{
	/**
	 * Creates new StringVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public StringVerifierImpl(ApplicationScope scope, String name, String actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public StringVerifier isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be empty", name)).
			build();
	}

	@Override
	public StringVerifier trim()
	{
		String trimmed = actual.trim();
		if (trimmed.equals(actual))
			return this;
		return new StringVerifierImpl(scope, name + ".trim()", trimmed, config);
	}

	@Override
	public StringVerifier isTrimmed()
	{
		String trimmed = actual.trim();
		if (trimmed.equals(actual))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain leading or trailing whitespace", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		char firstCharacter = actual.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s must contain a valid IP address or hostname format.", name)).
				addContext("Actual", actual).
				build();
		}
		InetAddress address;
		try
		{
			address = InetAddress.getByName(actual);
		}
		catch (UnknownHostException e)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s must contain a valid IP address or hostname format.", name), e).
				addContext("Actual", actual).
				build();
		}
		return new InetAddressVerifierImpl(scope, name, address, config);
	}

	@Override
	public StringVerifier asInetAddress(Consumer<InetAddressVerifier> consumer)
	{
		consumer.accept(asInetAddress());
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		try
		{
			URI uri = URI.create(actual);
			return new UriVerifierImpl(scope, name, uri, config);
		}
		catch (IllegalArgumentException unused)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s does not contain a valid URI format", name)).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public StringVerifier asUri(Consumer<UriVerifier> consumer)
	{
		consumer.accept(asUri());
		return this;
	}

	@Override
	public UrlVerifier asUrl()
	{
		try
		{
			URL url = new URL(actual);
			return new UrlVerifierImpl(scope, name, url, config);
		}
		catch (MalformedURLException e)
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				String.format("%s is not a valid URL", name), e).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public StringVerifier asUrl(Consumer<UrlVerifier> consumer)
	{
		consumer.accept(asUrl());
		return this;
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		if (actual.startsWith(prefix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must start with \"%s\".", name, prefix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		if (!actual.startsWith(prefix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not start with \"%s\".", name, prefix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		if (actual.endsWith(suffix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must end with \"%s\".", name, suffix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		if (!actual.endsWith(suffix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not end with \"%s\".", name, suffix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier contains(String expected)
	{
		if (actual.contains(expected))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must contain \"%s\".", name, expected)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotContain(String value)
	{
		if (!actual.contains(value))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not contain \"%s\".", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> length()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".length()",
			actual.length(), Pluralizer.CHARACTER, config);
	}

	@Override
	public StringVerifier length(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(length());
		return this;
	}
}
