/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.ext;

/**
 * Verifies a {@link Comparable}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public interface ComparableVerifierExtension<S extends ComparableVerifierExtension<S, T>, T extends Comparable<? super T>>
	extends ObjectVerifierExtension<S, T>
{
	/**
	 * Ensures that the actual value is greater than the specified the specified value.
	 *
	 * @param value a lower bound
	 * @param name  the name of the lower bound
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value};
	 *                                  if {@code name} is empty
	 */
	S isGreaterThan(T value, String name);

	/**
	 * Ensures that the actual value is greater than the specified value.
	 *
	 * @param value a lower bound
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value}
	 */
	S isGreaterThan(T value);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @param name  the name of the minimum value
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}; if
	 *                                  {@code name} is empty
	 */
	S isGreaterThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}
	 */
	S isGreaterThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is less than the value of the specified value.
	 *
	 * @param value the upper bound
	 * @param name  the name of the upper bound
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to
	 *                                  {@code value}; if {@code name} is empty
	 */
	S isLessThan(T value, String name);

	/**
	 * Ensures that the actual value is less than the specified value.
	 *
	 * @param value the upper bound
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to {@code value}
	 */
	S isLessThan(T value);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @param name  the name of the maximum value
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}; if
	 *                                  {@code name} is empty
	 */
	S isLessThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}
	 */
	S isLessThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param min the minimum element in the range (inclusive)
	 * @param max the maximum element in the range (inclusive)
	 * @return this
	 * @throws NullPointerException     if {@code min} or {@code max} are null
	 * @throws IllegalArgumentException if {@code max} is less than {@code min}; if
	 *                                  the actual value is not in range
	 */
	S isBetween(T min, T max);
}
