/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.OptionalValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.Optional;

/**
 * Default implementation of {@code OptionalValidator}.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalValidatorImpl extends AbstractObjectValidator<OptionalValidator, Optional<?>>
	implements OptionalValidator
{
	/**
	 * @param scope  the application configuration
	 * @param config the instance configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @throws AssertionError if {@code scope}, {@code config} or {@code name} are null. If
	 *                        {@code name} is empty.
	 */
	public OptionalValidatorImpl(ApplicationScope scope, Configuration config, String name, Optional<?> actual)
	{
		super(scope, config, name, actual, NO_FAILURES);
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
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.isEmpty())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be present");
			addFailure(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator isEmpty()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.isPresent())
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be empty.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return this;
	}

	@Override
	public OptionalValidator contains(Object value)
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (value == null)
			return isEmpty();
		Optional<?> expected = Optional.of(value);
		if (!actual.equals(expected))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
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
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		Optional<?> expectedAsOptional = Optional.ofNullable(expected);
		if (!actual.equals(expectedAsOptional))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must contain " + name + ".").
				addContext("Actual", actual).
				addContext("Expected", expectedAsOptional);
			addFailure(failure);
		}
		return this;
	}
}
