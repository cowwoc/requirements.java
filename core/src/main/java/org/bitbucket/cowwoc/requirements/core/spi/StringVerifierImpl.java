/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.EmailAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.UriVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.InetAddressVerifier;

/**
 * Default implementation of StringVerifier.
 *
 * @author Gili Tzabari
 */
public final class StringVerifierImpl implements StringVerifier
{
	private final static Pattern EMAIL_PATTERN = Pattern.compile("[^@]+@[^@]+");
	private final SingletonScope scope;
	private final String actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<String> asObject;

	/**
	 * Creates new StringVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public StringVerifierImpl(SingletonScope scope, String actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public StringVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new StringVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public StringVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new StringVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public StringVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new StringVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public StringVerifier isEmpty()
	{
		if (actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier isNotEmpty()
	{
		if (!actual.isEmpty())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
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
	public EmailAddressVerifier asEmailAddress()
	{
		if (EMAIL_PATTERN.matcher(actual).matches())
			return new EmailAddressVerifierImpl(scope, actual, name, config);
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s does not contain a valid email format", name)).
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
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format", name)).
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
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s does not contain a valid IP address format", name), e).
				addContext("Actual", actual).
				build();
		}
		return new InetAddressVerifierImpl(scope, address, name, config);
	}

	@Override
	public UriVerifier asUri()
	{
		try
		{
			URI uri = URI.create(actual);
			return new UriVerifierImpl(scope, uri, name, config);
		}
		catch (IllegalArgumentException e)
		{
			throw config.exceptionBuilder(IllegalArgumentException.class,
				String.format("%s does not contain a valid URI format", name)).
				addContext("Actual", actual).
				build();
		}
	}

	@Override
	public StringVerifier startsWith(String prefix)
	{
		if (actual.startsWith(prefix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must start with \"%s\".", name, prefix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotStartWith(String prefix)
	{
		if (!actual.startsWith(prefix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not start with \"%s\".", name, prefix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier endsWith(String suffix)
	{
		if (actual.endsWith(suffix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must end with \"%s\".", name, suffix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier doesNotEndWith(String suffix)
	{
		if (!actual.endsWith(suffix))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not end with \"%s\".", name, suffix)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return new ContainerSizeVerifierImpl(scope, actual, actual.length(), name,
			name + ".length()", Pluralizer.CHARACTER, config);
	}

	@Override
	public StringVerifier isEqualTo(String value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public StringVerifier isEqualTo(String value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public StringVerifier isNotEqualTo(String value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public StringVerifier isIn(Collection<String> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public StringVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public StringVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public StringVerifier isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return this;
	}

	@Override
	public Optional<String> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public String getActual()
	{
		return actual;
	}

	@Override
	public StringVerifier isolate(Consumer<StringVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
