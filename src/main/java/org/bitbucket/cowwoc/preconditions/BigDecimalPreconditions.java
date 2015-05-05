/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.math.BigDecimal;

/**
 * Verifies preconditions of a {@link BigDecimal} parameter.
 * <p>
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface BigDecimalPreconditions
	extends NumberPreconditions<BigDecimalPreconditions, BigDecimal>
{
	/**
	 * @return preconditions over BigDecimal.precision()
	 */
	BigDecimalPrecisionPreconditions precision();

	/**
	 * @return preconditions over BigDecimal.scale()
	 */
	BigDecimalScalePreconditions scale();
}
