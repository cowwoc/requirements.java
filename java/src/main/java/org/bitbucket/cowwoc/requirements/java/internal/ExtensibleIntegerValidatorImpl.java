/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Extensible implementation of {@code ExtensibleIntegerValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class ExtensibleIntegerValidatorImpl<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberValidator<S, T>
	implements ExtensibleIntegerValidator<S, T>
{
	/**
	 * @param scope    the application configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code name}, {@code config} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	protected ExtensibleIntegerValidatorImpl(ApplicationScope scope, String name, T actual,
	                                         Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " may not be a whole number.").
			addContext("Actual", actual);
		failures.add(failure);
		return getThis();
	}
}