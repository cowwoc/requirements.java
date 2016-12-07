/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.NumberVerifierSpi;

/**
 * Verifies a {@link Double} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface DoubleVerifier
	extends NumberVerifierSpi<DoubleVerifier, Double>, 
	Isolatable<DoubleVerifier>
{
	/**
	 * Ensures that the actual value is a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not a number
	 * @see Double#isNaN()
	 */
	DoubleVerifier isNumber();

	/**
	 * Ensures that the actual value is not a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is a number
	 * @see Double#isNaN()
	 */
	DoubleVerifier isNotNumber();

	/**
	 * Ensures that the actual value is finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not finite
	 * @see Double#isInfinite()
	 */
	DoubleVerifier isFinite();

	/**
	 * Ensures that the actual value is not finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is finite
	 * @see Double#isInfinite()
	 */
	DoubleVerifier isNotFinite();
}
