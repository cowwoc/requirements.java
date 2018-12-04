/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveNumberCapabilities;

import java.math.BigDecimal;

/**
 * Verifies a {@link BigDecimal#precision()}.
 */
public interface BigDecimalPrecisionVerifier
	extends PrimitiveNumberCapabilities<BigDecimalPrecisionVerifier, Integer>
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
