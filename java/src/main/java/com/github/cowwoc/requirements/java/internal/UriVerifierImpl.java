/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.UriValidator;
import com.github.cowwoc.requirements.java.UriVerifier;
import com.github.cowwoc.requirements.java.UrlValidator;
import com.github.cowwoc.requirements.java.UrlVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

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
		validator.isAbsolute();
		return validationResult(IllegalArgumentException.class);
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
