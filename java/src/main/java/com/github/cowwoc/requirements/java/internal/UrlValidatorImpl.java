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
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	public UrlValidatorImpl(ApplicationScope scope, Configuration config, String name, URL actual,
		List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name, actual, failures, fatalFailure);
	}

	@Override
	protected UrlValidator getThis()
	{
		return this;
	}

	@Override
	public UriValidator asUri()
	{
		if (fatalFailure)
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a URI.").
				addContext("Actual", this.actual);
			addFailure(failure);
			fatalFailure = true;
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
		try
		{
			URI uri = actual.toURI();
			return new UriValidatorImpl(scope, config, name, uri, getFailures(), fatalFailure);
		}
		catch (URISyntaxException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " is not a valid URI").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			fatalFailure = true;
			return new UriValidatorImpl(scope, config, name, null, getFailures(), fatalFailure);
		}
	}

	@Override
	public UrlValidator asUri(Consumer<UriValidator> consumer)
	{
		if (fatalFailure)
			return this;
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			fatalFailure = true;
			return this;
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUri());
		return this;
	}
}
