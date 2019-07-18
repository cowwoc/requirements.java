/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.ValidationFailureImpl;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * Default implementation of {@code ExtensibleNumberValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractNumberValidator<S, T extends Number & Comparable<? super T>>
	extends AbstractComparableValidator<S, T>
	implements ExtensibleNumberValidator<S, T>
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
	protected AbstractNumberValidator(ApplicationScope scope, Configuration config, String name, T actual,
	                                  List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	public S isNegative()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.doubleValue() >= 0L)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be negative.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotNegative()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.doubleValue() < 0L)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be negative.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isZero()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.doubleValue() != 0L)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be zero.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotZero()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.doubleValue() == 0L)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be zero");
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isPositive()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		double actualAsDouble = actual.doubleValue();
		if (actualAsDouble <= 0L || Double.isNaN(actualAsDouble))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be positive.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotPositive()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (actual.doubleValue() > 0L)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be positive.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isWholeNumber()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!isWholeNumber(actual.doubleValue()))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be a whole number.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(double value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1) == 0;
	}

	@Override
	public S isNotWholeNumber()
	{
		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		// Based on https://stackoverflow.com/a/12748321/14731
		if (isWholeNumber(actual.doubleValue()))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be a whole number.").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();

		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble == 0 || !isWholeNumber(actual.doubleValue() / divisorAsDouble))
		{
			String divisorAsString = config.toString(divisor);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be a multiple of " + divisorAsString + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		double divisorAsDouble = divisor.doubleValue();
		// TODO: Is zero really a multiple of all numbers?
		if (divisorAsDouble == 0 || !isWholeNumber(actual.doubleValue() / divisorAsDouble))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be a multiple of " + name + ".").
				addContext("Actual", actual).
				addContext("divisor", divisor);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();

		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && isWholeNumber(actual.doubleValue() / divisorAsDouble))
		{
			String divisorAsString = config.toString(divisor);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be a multiple of " + divisorAsString + ".").
				addContext("Actual", actual);
			addFailure(failure);
		}
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();

		if (actual == null)
		{
			addFailure(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && !isWholeNumber(actual.doubleValue() / divisorAsDouble))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not be a multiple of " + name + ".").
				addContext("Actual", actual).
				addContext("divisor", divisor);
			addFailure(failure);
		}
		return getThis();
	}
}
