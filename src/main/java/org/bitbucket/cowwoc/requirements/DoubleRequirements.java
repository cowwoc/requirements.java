/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.NumberRequirementsSpi;

/**
 * Verifies requirements of a {@link Double} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface DoubleRequirements
	extends NumberRequirementsSpi<DoubleRequirements, Double>,
	Isolatable<DoubleRequirements>
{
	/**
	 * Ensures that a Double is a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is not a number
	 * @see Double#isNaN()
	 */
	DoubleRequirements isNumber() throws IllegalArgumentException;

	/**
	 * Ensures that a Double is not a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is a number
	 * @see Double#isNaN()
	 */
	DoubleRequirements isNotNumber() throws IllegalArgumentException;

	/**
	 * Ensures that a Double is finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is not finite
	 * @see Double#isInfinite()
	 */
	DoubleRequirements isFinite() throws IllegalArgumentException;

	/**
	 * Ensures that a Double is not finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if parameter is finite
	 * @see Double#isInfinite()
	 */
	DoubleRequirements isNotFinite() throws IllegalArgumentException;
}
