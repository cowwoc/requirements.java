/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.math.BigDecimal;
import java.util.List;

/**
 * Default implementation of {@code BigDecimalPrecisionValidator}.
 */
public final class BigDecimalPrecisionValidatorImpl
	extends AbstractNumberValidator<BigDecimalPrecisionValidator, Integer>
	implements BigDecimalPrecisionValidator
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
	public BigDecimalPrecisionValidatorImpl(ApplicationScope scope, String name, BigDecimal actual,
	                                        Configuration config, List<ValidationFailure> failures)
	{
		super(scope, name + ".precision()", actual.precision(), config, failures);
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isZero()
	{
		return neverZero();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isNotZero()
	{
		return neverZero();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isNotPositive()
	{
		return neverZeroOrNegative();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isPositive()
	{
		return neverZeroOrNegative();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isNotNegative()
	{
		return neverNegative();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionValidator isNegative()
	{
		return neverNegative();
	}

	private BigDecimalPrecisionValidator neverZero()
	{
		ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " can never be zero");
		failures.add(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverZeroOrNegative()
	{
		ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " can never be zero or negative");
		failures.add(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverNegative()
	{
		ValidationFailure failure = new ValidationFailureImpl(IllegalArgumentException.class,
			name + " can never be negative");
		failures.add(failure);
		return this;
	}
}
