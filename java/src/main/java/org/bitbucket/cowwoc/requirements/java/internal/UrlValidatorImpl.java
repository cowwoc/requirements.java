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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code UrlValidator}.
 */
public final class UrlValidatorImpl extends AbstractObjectValidator<UrlValidator, URL>
	implements UrlValidator
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
	public UrlValidatorImpl(ApplicationScope scope, Configuration config, String name, URL actual,
	                        List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected UrlValidator getThis()
	{
		return this;
	}

	@Override
	protected UrlValidator getNoOp()
	{
		return new UrlValidatorNoOp(scope, config, failures);
	}

	@Override
	public UriValidator asUri()
	{
		try
		{
			URI uri = actual.toURI();
			return new UriValidatorImpl(scope, config, name, uri, failures);
		}
		catch (URISyntaxException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " is not a valid URI").
				setCause(e).
				addContext("Actual", actual);
			failures.add(failure);
			return new UriValidatorNoOp(scope, config, failures);
		}
	}

	@Override
	public UrlValidator asUri(Consumer<UriValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(asUri());
		return this;
	}
}
