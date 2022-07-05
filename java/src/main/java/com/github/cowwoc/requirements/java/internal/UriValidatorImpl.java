/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.UriValidator;
import com.github.cowwoc.requirements.java.UrlValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 *                        {@code name} is blank.
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
		return new UriValidatorNoOp(getFailures());
	}

	@Override
	public UriValidator isAbsolute()
	{
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (!actual.isAbsolute())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be absolute.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public UrlValidator asUrl()
	{
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a URL.").
				addContext("Actual", this.actual);
			addFailure(failure);
			return new UrlValidatorNoOp(getFailures());
		}
		try
		{
			URL url = actual.toURL();
			return new UrlValidatorImpl(scope, config, name, url, getFailures());
		}
		catch (MalformedURLException | IllegalArgumentException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " is not a valid URL").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			return new UrlValidatorNoOp(getFailures());
		}
	}

	@Override
	public UriValidator asUrl(Consumer<UrlValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUrl());
		return this;
	}
}
