/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.net.URI;
import java.util.function.Consumer;

/**
 * Default implementation of {@code UriVerifier}.
 */
public final class UriVerifierImpl extends AbstractObjectVerifier<UriVerifier, UriValidator, URI>
	implements UriVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public UriVerifierImpl(UriValidator validator)
	{
		super(validator);
	}

	@Override
	protected UriVerifier getThis()
	{
		return this;
	}

	@Override
	public UriVerifier isAbsolute()
	{
		validator = validator.isAbsolute();
		return validationResult();
	}

	@Override
	public UrlVerifier asUrl()
	{
		UrlValidator newValidator = validator.asUrl();
		return validationResult(() -> new UrlVerifierImpl(newValidator));
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
