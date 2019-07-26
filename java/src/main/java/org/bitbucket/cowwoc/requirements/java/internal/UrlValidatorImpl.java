/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
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
	 * Creates a UrlValidatorImpl with no validation failures.
	 *
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If {@code name} is
	 *                        empty.
	 */
	public UrlValidatorImpl(ApplicationScope scope, Configuration config, String name, URL actual)
	{
		this(scope, config, name, actual, NO_FAILURES);
	}

	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	UrlValidatorImpl(ApplicationScope scope, Configuration config, String name, URL actual,
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
		return new UrlValidatorNoOp(getFailures());
	}

	@Override
	public UriValidator asUri()
	{
		if (actual == null)
		{
			ValidationFailureImpl failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " must be a URI.").
				addContext("Actual", this.actual);
			addFailure(failure);
			return new UriValidatorNoOp(getFailures());
		}
		try
		{
			URI uri = actual.toURI();
			return new UriValidatorImpl(scope, config, name, uri, getFailures());
		}
		catch (URISyntaxException e)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " is not a valid URI").
				setCause(e).
				addContext("Actual", actual);
			addFailure(failure);
			return new UriValidatorNoOp(getFailures());
		}
	}

	@Override
	public UrlValidator asUri(Consumer<UriValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(asUri());
		return this;
	}
}
