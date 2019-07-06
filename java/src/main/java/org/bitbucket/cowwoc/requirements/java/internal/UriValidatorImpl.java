/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.UriValidator;
import org.bitbucket.cowwoc.requirements.java.UrlValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code UriValidator}.
 */
public final class UriValidatorImpl extends AbstractObjectValidator<UriValidator, URI>
	implements UriValidator
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public UriValidatorImpl(ApplicationScope scope, Configuration config, String name, URI actual,
	                        List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected UriValidator getThis()
	{
		return this;
	}

	@Override
	protected UriValidator getNoOp()
	{
		return new UriValidatorNoOp(scope, config, failures);
	}

	@Override
	public UriValidator isAbsolute()
	{
		if (!actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be absolute.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		try
		{
			URL url = actual.toURL();
			return new UrlValidatorImpl(scope, config, name, url, failures);
		}
		catch (MalformedURLException | IllegalArgumentException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UrlValidatorNoOp(scope, config, failures);
		}
	}

	@Override
	public UriValidator asUrl(Consumer<UrlValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUrl());
		return this;
	}
}
