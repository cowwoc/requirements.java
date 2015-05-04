/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * Verifies preconditions of a {@link BigDecimal} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface BigDecimalPreconditions
	extends NumberPreconditions<BigDecimalPreconditions, BigDecimal>
{
	/**
	 * Ensures that the parameter's precision is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the precision is not in range
	 */
	BigDecimalPreconditions hasPrecisionIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter's precision is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the precision is not in range
	 */
	BigDecimalPreconditions hasScaleIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException;
}
