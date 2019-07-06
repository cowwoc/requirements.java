/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.OptionalValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@code OptionalValidator}.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalValidatorImpl extends AbstractObjectValidator<OptionalValidator, Optional<?>>
	implements OptionalValidator
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
	public OptionalValidatorImpl(ApplicationScope scope, Configuration config, String name, Optional<?> actual,
	                             List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected OptionalValidator getThis()
	{
		return this;
	}

	@Override
	protected OptionalValidator getNoOp()
	{
		return new OptionalValidatorNoOp(scope, config, failures);
	}

	@Override
	public OptionalValidator isPresent()
	{
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be present");
			failures.add(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator isEmpty()
	{
		if (actual.isPresent())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator contains(Object value)
	{
		if (value == null)
			return isEmpty();
		Optional<?> expected = Optional.of(value);
		if (!actual.equals(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must contain " + config.toString(value) + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator contains(Object expected, String name)
	{
		Optional<?> expectedAsOptional = Optional.ofNullable(expected);
		if (!actual.equals(expectedAsOptional))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expectedAsOptional);
			failures.add(failure);
		}
		return this;
	}
}
