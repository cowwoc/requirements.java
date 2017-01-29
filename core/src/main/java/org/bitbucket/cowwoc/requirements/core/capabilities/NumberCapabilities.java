/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import org.bitbucket.cowwoc.requirements.core.NumberVerifier;

/**
 * Verifies a value that extends {@link Number} but the implementing verifier is not guaranteed
 * to be a {@link NumberVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public interface NumberCapabilities<S, T extends Number & Comparable<? super T>>
	extends ComparableCapabilities<S, T>
{
	/**
	 * Ensures that the actual value is negative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not negative
	 */
	S isNegative();

	/**
	 * Ensures that the actual value is not negative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is negative
	 */
	S isNotNegative();

	/**
	 * Ensures that the actual value is zero.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not zero
	 */
	S isZero();

	/**
	 * Ensures that the actual value is not zero.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is zero
	 */
	S isNotZero();

	/**
	 * Ensures that the actual value is positive.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not positive
	 */
	S isPositive();

	/**
	 * Ensures that the actual value is not positive.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is positive
	 */
	S isNotPositive();
}
