/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.EmailAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;

/**
 * Default implementation of EmailAddressVerifierImpl.
 *
 * @author Gili Tzabari
 */
public final class EmailAddressVerifierImpl implements EmailAddressVerifier
{
	private final SingletonScope scope;
	private final String actual;
	private final String name;
	private final Configuration config;
	private final StringVerifier asString;

	/**
	 * Creates new EmailAddressVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public EmailAddressVerifierImpl(SingletonScope scope, String actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asString = new StringVerifierImpl(scope, actual, name, config);
	}

	@Override
	public EmailAddressVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new EmailAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public EmailAddressVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new EmailAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public EmailAddressVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new EmailAddressVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public EmailAddressVerifier doesNotEndWith(String suffix)
	{
		asString.doesNotEndWith(suffix);
		return this;
	}

	@Override
	public EmailAddressVerifier doesNotStartWith(String prefix)
	{
		asString.doesNotStartWith(prefix);
		return this;
	}

	@Override
	public EmailAddressVerifier endsWith(String suffix)
	{
		asString.endsWith(suffix);
		return this;
	}

	@Override
	public EmailAddressVerifier isEmpty()
	{
		asString.isEmpty();
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEmpty()
	{
		asString.isNotEmpty();
		return this;
	}

	@Override
	public EmailAddressVerifier startsWith(String prefix)
	{
		asString.startsWith(prefix);
		return this;
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return asString.length();
	}

	@Override
	public EmailAddressVerifier isEqualTo(String value)
	{
		asString.isEqualTo(value);
		return this;
	}

	@Override
	public EmailAddressVerifier isEqualTo(String value, String name)
	{
		asString.isEqualTo(value, name);
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEqualTo(String value)
	{
		asString.isNotEqualTo(value);
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEqualTo(String value, String name)
	{
		asString.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public EmailAddressVerifier isIn(Collection<String> collection)
	{
		asString.isIn(collection);
		return this;
	}

	@Override
	public EmailAddressVerifier isInstanceOf(Class<?> type)
	{
		asString.isInstanceOf(type);
		return this;
	}

	@Override
	public EmailAddressVerifier isNull()
	{
		asString.isNull();
		return this;
	}

	@Override
	public EmailAddressVerifier isNotNull()
	{
		asString.isNotNull();
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return asString;
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
	public EmailAddressVerifier isolate(Consumer<EmailAddressVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
