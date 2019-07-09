/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UriVerifier;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.UrlVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

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
