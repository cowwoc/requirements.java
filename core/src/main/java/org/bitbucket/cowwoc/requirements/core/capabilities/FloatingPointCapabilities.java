/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.FloatingPointVerifier;

/**
 * Verifies a floating-point value but the implementing verifier is not guaranteed to be a
 * {@link FloatingPointVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public interface FloatingPointCapabilities<S, T extends Number & Comparable<? super T>>
	extends NumberCapabilities<S, T>
{
	/**
	 * Ensures that the actual value is a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not a number
	 * @see Double#isNaN()
	 */
	S isNumber();

	/**
	 * Ensures that the actual value is not a number.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is a number
	 * @see Double#isNaN()
	 */
	S isNotNumber();

	/**
	 * Ensures that the actual value is finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is not finite
	 * @see Double#isInfinite()
	 */
	S isFinite();

	/**
	 * Ensures that the actual value is not finite.
	 *
	 * @return this
	 * @throws IllegalArgumentException if actual value is finite
	 * @see Double#isInfinite()
	 */
	S isNotFinite();
}
