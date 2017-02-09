/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of StringVerifier.
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
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public StringVerifierImpl(ApplicationScope scope, String actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
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
		return new StringVerifierImpl(scope, trimmed, name + ".trim()", config);
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
		return new InetAddressVerifierImpl(scope, address, name, config);
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
			return new UriVerifierImpl(scope, uri, name, config);
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
	public PrimitiveIntegerVerifier length()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.length(), name,
			name + ".length()", Pluralizer.CHARACTER, config);
	}

	@Override
	public StringVerifier length(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		consumer.accept(length());
		return this;
	}
}
