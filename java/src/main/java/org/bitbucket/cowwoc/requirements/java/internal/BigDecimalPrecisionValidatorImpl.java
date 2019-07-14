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
	 * @param config   the instance configuration
	 * @param name     the name of the value
	 * @param actual   the actual value
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is empty.
	 */
	public BigDecimalPrecisionValidatorImpl(ApplicationScope scope, Configuration config, String name,
	                                        BigDecimal actual, List<ValidationFailure> failures)
	{
		super(scope, config, name + ".precision()", actual.precision(), failures);
	}

	@Override
	protected BigDecimalPrecisionValidator getThis()
	{
		return this;
	}

	@Override
	protected BigDecimalPrecisionValidator getNoOp()
	{
		return new BigDecimalPrecisionValidatorNoOp(getFailures());
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isZero()
	{
		return neverZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotZero()
	{
		return neverZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotPositive()
	{
		return neverZeroOrNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isPositive()
	{
		return neverZeroOrNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotNegative()
	{
		return neverNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNegative()
	{
		return neverNegative();
	}

	private BigDecimalPrecisionValidator neverZero()
	{
		ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
			name + " can never be zero");
		addFailure(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverZeroOrNegative()
	{
		ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
			name + " can never be zero or negative");
		addFailure(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverNegative()
	{
		ValidationFailure failure = new ValidationFailureImpl(this, IllegalArgumentException.class,
			name + " can never be negative");
		addFailure(failure);
		return this;
	}
}
