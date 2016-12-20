/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.EmailAddressVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.ext.StringBasedExtension;

/**
 * An implementation of EmailAddressVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public enum NoOpEmailAddressVerifier implements EmailAddressVerifier
{
	INSTANCE;

	@Override
	public EmailAddressVerifier doesNotEndWith(String suffix)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier doesNotStartWith(String prefix)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier endsWith(String suffix)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isEmpty()
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEmpty()
	{
		return this;
	}

	@Override
	public EmailAddressVerifier startsWith(String prefix)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier length()
	{
		return NoOpContainerSizeVerifier.INSTANCE;
	}

	@Override
	public StringBasedExtension<EmailAddressVerifier, String> length(
		Consumer<ContainerSizeVerifier> consumer)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isEqualTo(String value)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEqualTo(String value)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isNotEqualTo(String value, String name)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isIn(Collection<String> collection)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isInstanceOf(
		Class<?> type)
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isNull()
	{
		return this;
	}

	@Override
	public EmailAddressVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return NoOpStringVerifier.INSTANCE;
	}

	@Override
	public EmailAddressVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<String> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public String getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}
}
