/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.NumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code NumberValidator}.
 *
 * @param <T> the type of the value being validated
 */
public final class NumberValidatorImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidator<NumberValidator<T>, T>
	implements NumberValidator<T>
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
	public NumberValidatorImpl(ApplicationScope scope, Configuration config, String name, T actual,
	                           List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected NumberValidator<T> getThis()
	{
		return this;
	}

	@Override
	protected NumberValidator<T> getNoOp()
	{
		return new NumberValidatorNoOp<>(failures);
	}
}
