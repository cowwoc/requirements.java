/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.ext.PrimitiveIntegerVerifierExtension;

/**
 * Verifies a {@link BigDecimal#precision()}.
 *
 * @author Gili Tzabari
 */
public interface BigDecimalPrecisionVerifier
	extends PrimitiveIntegerVerifierExtension<BigDecimalPrecisionVerifier>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be zero
	 */
	@Deprecated
	@Override
	BigDecimalPrecisionVerifier isZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be non-positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNotPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be negative
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionVerifier isNegative();
}
