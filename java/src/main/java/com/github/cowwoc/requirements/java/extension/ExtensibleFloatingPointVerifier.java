/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.FloatingPointVerifier;

/**
 * Verifies the requirements of a floating-point value but the implementing verifier is not guaranteed to be a
 * {@link FloatingPointVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public interface ExtensibleFloatingPointVerifier<S, T extends Number & Comparable<? super T>>
	extends ExtensibleNumberVerifier<S, T>
{
	/**
	 * Ensures that the actual value is a number.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not a number
	 * @see Double#isNaN()
	 */
	S isNumber();

	/**
	 * Ensures that the actual value is not a number.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is a number
	 * @see Double#isNaN()
	 */
	S isNotNumber();

	/**
	 * Ensures that the actual value is finite.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is not finite
	 * @see Double#isInfinite()
	 */
	S isFinite();

	/**
	 * Ensures that the actual value is not finite.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if actual value is finite
	 * @see Double#isInfinite()
	 */
	S isNotFinite();
}
