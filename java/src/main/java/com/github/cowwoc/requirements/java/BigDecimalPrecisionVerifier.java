/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

import java.math.BigDecimal;

/**
 * Verifies the requirements of a {@link BigDecimal#precision()}.
 */
public interface BigDecimalPrecisionVerifier
	extends ExtensiblePrimitiveVerifier<BigDecimalPrecisionVerifier, Integer>,
	ExtensibleNumberVerifier<BigDecimalPrecisionVerifier, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNotZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNotPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNegative();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNotNegative();
}
