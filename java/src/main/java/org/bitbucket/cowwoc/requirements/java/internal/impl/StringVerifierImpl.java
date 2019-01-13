/*
 * Copyright (c) 2013 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;
import org.bitbucket.cowwoc.requirements.java.internal.util.Pluralizer;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.function.Consumer;

/**
 * Default implementation of {@link StringVerifier}.
 */
public final class StringVerifierImpl extends ObjectCapabilitiesImpl<StringVerifier, String>
	implements StringVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected StringVerifierImpl(ApplicationScope scope, String name, String actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public StringVerifier isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be empty.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be empty").
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
			name + " may not contain leading or trailing whitespace").
			addContext("Actual", actual).
			build();
	}

	@Override
	public InetAddressVerifier asInetAddress()
	{
		// IPv4 must start with a digit. IPv6 must start with a colon.
		if (actual.isEmpty())
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				name + " may not be empty").
				build();
		}
		char firstCharacter = actual.charAt(0);
		if (Character.digit(firstCharacter, 16) == -1 && (firstCharacter != ':'))
		{
			throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
				name + " must contain a valid IP address or hostname format.").
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
				name + " must contain a valid IP address or hostname format.", e).
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
				name + " does not contain a valid URI format").
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
				name + " is not a valid URL", e).
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
			name + " must start with \"" + prefix + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		if (!actual.startsWith(prefix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not start with \"" + prefix + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		if (actual.endsWith(suffix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must end with \"" + suffix + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		if (!actual.endsWith(suffix))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not end with \"" + suffix + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier contains(String expected)
	{
		if (actual.contains(expected))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must contain \"" + expected + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotContain(String value)
	{
		if (!actual.contains(value))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not contain \"" + value + "\".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> length()
	{
		return new ContainerSizeVerifierImpl(scope, name, actual, name + ".length()", actual.length(), Pluralizer.CHARACTER, config);
	}

	@Override
	public StringVerifier length(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(length());
		return this;
	}
}
