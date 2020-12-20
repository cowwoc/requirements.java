/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.JavaRequirements;
import com.github.cowwoc.requirements.java.OptionalValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
		return new OptionalValidatorNoOp(getFailures());
	}

	@Override
	public OptionalValidator isPresent()
	{
		//noinspection OptionalAssignedToNull
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be present");
			addFailure(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator isEmpty()
	{
		//noinspection OptionalAssignedToNull
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (actual.isPresent())
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator contains(Object value)
	{
		//noinspection OptionalAssignedToNull
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		if (value == null)
			return isEmpty();
		Optional<?> expected = Optional.of(value);
		if (!actual.equals(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				name + " must contain " + config.toString(value) + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator contains(Object expected, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull();
		//noinspection OptionalAssignedToNull
		if (actual == null)
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, NullPointerException.class,
				this.name + " may not be null");
			addFailure(failure);
			return getNoOp();
		}
		Optional<?> expectedAsOptional = Optional.ofNullable(expected);
		if (!actual.equals(expectedAsOptional))
		{
			ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
				this.name + " must contain " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expectedAsOptional);
			addFailure(failure);
		}
		return this;
	}
}
