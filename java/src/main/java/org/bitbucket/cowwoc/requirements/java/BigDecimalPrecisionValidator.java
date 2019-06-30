/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveNumberValidator;

import java.math.BigDecimal;

/**
 * Validates the requirements of a {@link BigDecimal#precision()}.
 */
public interface BigDecimalPrecisionValidator
	extends ExtensiblePrimitiveNumberValidator<BigDecimalPrecisionValidator, Integer>
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
