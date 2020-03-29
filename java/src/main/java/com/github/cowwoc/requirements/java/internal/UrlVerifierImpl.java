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

import java.net.URL;
import java.util.function.Consumer;

/**
 * Default implementation of {@code UrlVerifier}.
 */
public final class UrlVerifierImpl extends AbstractObjectVerifier<UrlVerifier, UrlValidator, URL>
	implements UrlVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public UrlVerifierImpl(UrlValidator validator)
	{
		super(validator);
	}

	@Override
	protected UrlVerifier getThis()
	{
		return this;
	}

	@Override
	public UriVerifier asUri()
	{
		UriValidator newValidator = validator.asUri();
		return validationResult(() -> new UriVerifierImpl(newValidator));
	}

	@Override
	public UrlVerifier asUri(Consumer<UriVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUri());
		return this;
	}
}
