/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidator;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;

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
	 * @param scope        the application configuration
	 * @param config       the instance configuration
	 * @param name         the name of the value
	 * @param actual       the actual value
	 * @param failures     the list of validation failures
	 * @param fatalFailure true if validation stopped as the result of a fatal failure
	 * @throws AssertionError if {@code scope}, {@code config}, {@code name} or {@code failures} are null. If
	 *                        {@code name} is blank.
	 */
	BigDecimalPrecisionValidatorImpl(ApplicationScope scope, Configuration config, String name,
		BigDecimal actual, List<ValidationFailure> failures, boolean fatalFailure)
	{
		super(scope, config, name + ".precision()", actual.precision(), failures, fatalFailure);
	}

	@Override
	protected BigDecimalPrecisionValidator getThis()
	{
		return this;
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
		if (fatalFailure)
			return this;
		ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be zero");
		addFailure(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverZeroOrNegative()
	{
		if (fatalFailure)
			return this;
		ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be zero or negative");
		addFailure(failure);
		return this;
	}

	private BigDecimalPrecisionValidator neverNegative()
	{
		if (fatalFailure)
			return this;
		ValidationFailure failure = new ValidationFailureImpl(scope, config, IllegalArgumentException.class,
			name + " can never be negative");
		addFailure(failure);
		return this;
	}
}
