/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * Default implementation of {@code BigDecimalValidator}.
 */
public final class BigDecimalValidatorImpl
	extends AbstractNumberValidator<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
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
	public BigDecimalValidatorImpl(ApplicationScope scope, Configuration config, String name, BigDecimal actual,
	                               List<ValidationFailure> failures)
	{
		super(scope, config, name, actual, failures);
	}

	@Override
	protected BigDecimalValidator getThis()
	{
		return this;
	}

	@Override
	protected BigDecimalValidator getNoOp()
	{
		return new BigDecimalValidatorNoOp(failures);
	}

	@Override
	public BigDecimalValidator isZero()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		// We cannot use Number.longValue() because it truncates the fractional component of the number, which we
		// need to take into account.
		if (actual.signum() != 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be zero").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotZero()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		// We cannot use Number.longValue() because it truncates the fractional component of the number, which we
		// need to take into account.
		if (actual.signum() == 0)
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be zero");
			failures.add(failure);
		}
		return this;
	}

	@Override
	public BigDecimalPrecisionValidator precision()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new BigDecimalPrecisionValidatorNoOp(failures);
		}
		return new BigDecimalPrecisionValidatorImpl(scope, config, name, actual, failures);
	}

	@Override
	public BigDecimalValidator precision(Consumer<BigDecimalPrecisionValidator> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(precision());
		return this;
	}

	@Override
	public PrimitiveNumberValidator<Integer> scale()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return new PrimitiveNumberValidatorNoOp<>(failures);
		}
		return new BigDecimalScaleValidatorImpl(scope, config, name, actual, failures);
	}

	@Override
	public BigDecimalValidator scale(Consumer<PrimitiveNumberValidator<Integer>> consumer)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(consumer, "consumer").isNotNull();

		consumer.accept(scale());
		return this;
	}

	@Override
	public BigDecimalValidator isWholeNumber()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		if (!isWholeNumber(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be a whole number.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(BigDecimal value)
	{
		// Based on https://stackoverflow.com/a/12748321/14731
		return value.signum() == 0 || value.stripTrailingZeros().scale() <= 0;
	}

	@Override
	public BigDecimalValidator isNotWholeNumber()
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		// Based on https://stackoverflow.com/a/12748321/14731
		if (isWholeNumber(actual))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be a whole number.").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(BigDecimal number, BigDecimal factor)
	{
		return factor.compareTo(BigDecimal.ZERO) != 0 &&
			(number.compareTo(BigDecimal.ZERO) == 0 || number.remainder(factor).compareTo(BigDecimal.ZERO) == 0);
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal divisor)
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		if (!isMultipleOf(actual, divisor))
		{
			String divisorAsString = config.toString(divisor);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " must be a multiple of " + divisorAsString + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal divisor, String name)
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!isMultipleOf(actual, divisor))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " must be a multiple of " + name + ".").
				addContext("Actual", actual).
				addContext("divisor", divisor);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal divisor)
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		if (isMultipleOf(actual, divisor))
		{
			String divisorAsString = config.toString(divisor);
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				name + " may not be a multiple of " + divisorAsString + ".").
				addContext("Actual", actual);
			failures.add(failure);
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal divisor, String name)
	{
		if (actual == null)
		{
			failures.add(new ValidationFailureImpl(this, NullPointerException.class,
				this.name + " may not be null"));
			return getNoOp();
		}
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (isMultipleOf(actual, divisor))
		{
			ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
				this.name + " may not be a multiple of " + name + ".").
				addContext("Actual", actual).
				addContext("divisor", divisor);
			failures.add(failure);
		}
		return this;
	}
}
