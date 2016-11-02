/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies requirements of a {@link BigDecimal#precision()}.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalPrecisionRequirements
	extends PrimitiveIntegerRequirementsSpi<BigDecimalPrecisionRequirements>,
	Isolatable<BigDecimalPrecisionRequirements>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be zero
	 */
	@Deprecated
	@Override
	BigDecimalPrecisionRequirements isZero();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be non-positive
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionRequirements isNotPositive();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated {@code BigDecimal.precision()} cannot be negative
	 */
	@Override
	@Deprecated
	BigDecimalPrecisionRequirements isNegative();
}
