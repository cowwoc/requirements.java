/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.NumberVerifier;

/**
 * Verifies the requirements of a value that extends {@link Number} but the implementing verifier is not
 * guaranteed to be a {@link NumberVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public interface ExtensibleNumberVerifier<S, T extends Number & Comparable<? super T>>
	extends ExtensibleComparableVerifier<S, T>
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

	/**
	 * Ensures that the actual value is a whole value.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not a whole number
	 */
	S isWholeNumber();

	/**
	 * Ensures that the actual value is not a whole value.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is a whole number
	 */
	S isNotWholeNumber();

	/**
	 * Ensures that the actual value is a multiple of the specified value.
	 *
	 * @param divisor the value being divided by
	 * @return this
	 * @throws NullPointerException     if {@code divisor} is null
	 * @throws IllegalArgumentException if the actual value is not a multiple of {@code divisor}
	 */
	S isMultipleOf(T divisor);

	/**
	 * Ensures that the actual value is a multiple of the specified value.
	 *
	 * @param divisor the value being divided by
	 * @param name    the name of the divisor
	 * @return this
	 * @throws NullPointerException     if {@code divisor} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is not a multiple of {@code divisor}. If
	 *                                  {@code name} is empty.
	 */
	S isMultipleOf(T divisor, String name);

	/**
	 * Ensures that the actual value is not a multiple of the specified value.
	 *
	 * @param divisor the value being divided by
	 * @return this
	 * @throws NullPointerException     if {@code divisor} is null
	 * @throws IllegalArgumentException if the actual value is a multiple of {@code divisor}
	 */
	S isNotMultipleOf(T divisor);

	/**
	 * Ensures that the actual value is not a multiple of the specified value.
	 *
	 * @param divisor the value being divided by
	 * @param name    the name of the divisor
	 * @return this
	 * @throws NullPointerException     if {@code divisor} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is a multiple of {@code divisor}. If
	 *                                  {@code name} is empty.
	 */
	S isNotMultipleOf(T divisor, String name);
}
