/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Extensible implementation of {@code ExtensibleFloatingPointValidator} for {@code float}s.
 *
 * @param <S> the type of validator returned by the methods
 */
public abstract class AbstractFloatValidator<S>
	extends AbstractNumberValidator<S, Float>
	implements ExtensibleFloatingPointValidator<S, Float>
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
	protected AbstractFloatValidator(ApplicationScope scope, Configuration config, String name, Float actual,
	                                 List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	public S isNumber()
	{
		if (actual.isNaN())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
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
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
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
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
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
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be infinite.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return getThis();
	}
}
