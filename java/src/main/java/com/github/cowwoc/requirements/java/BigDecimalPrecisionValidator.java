/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import com.github.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

import java.math.BigDecimal;

/**
 * Validates the requirements of a {@link BigDecimal#precision()}.
 */
public interface BigDecimalPrecisionValidator
	extends ExtensiblePrimitiveValidator<BigDecimalPrecisionValidator, Integer>,
	ExtensibleNumberValidator<BigDecimalPrecisionValidator, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isNotZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isNotPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isNegative();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionValidator isNotNegative();
}
