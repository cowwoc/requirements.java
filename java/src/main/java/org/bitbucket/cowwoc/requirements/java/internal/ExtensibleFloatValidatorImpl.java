/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Extensible implementation of {@code ExtensibleFloatingPointValidator} for {@code float}s.
 *
 * @param <S> the type of validator returned by the methods
 */
public abstract class ExtensibleFloatValidatorImpl<S>
	extends AbstractNumberValidator<S, Float>
	implements ExtensibleFloatingPointValidator<S, Float>
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
	protected ExtensibleFloatValidatorImpl(ApplicationScope scope, String name, Float actual,
	                                       Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name, actual, config, failures);
	}

	@Override
	public S isNumber()
	{
		if (actual.isNaN())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be a number.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotNumber()
	{
		if (!actual.isNaN())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " may not be a number.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isFinite()
	{
		if (actual.isInfinite())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be finite.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}

	@Override
	public S isNotFinite()
	{
		if (!actual.isInfinite())
		{
			ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
				name + " must be infinite.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}
}
