/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.capabilities;

import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;

/**
 * Verifies the requirements of a value that extends {@link Comparable} but the implementing verifier is not guaranteed
 * to be a {@link ComparableVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public interface ComparableCapabilities<S, T extends Comparable<? super T>>
	extends ObjectCapabilities<S, T>
{
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
	 * Ensures that the actual value is greater than the specified the specified value.
	 *
	 * @param name  the name of the lower bound
	 * @param value a lower bound
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is less than or
	 *                                  equal to {@code value}
	 */
	S isGreaterThan(String name, T value);

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
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param name  the name of the minimum value
	 * @param value the minimum value
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is less than to
	 *                                  {@code value}
	 */
	S isGreaterThanOrEqualTo(String name, T value);

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
	 * Ensures that the actual value is less than the value of the specified value.
	 *
	 * @param name  the name of the upper bound
	 * @param value the upper bound
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is greater than
	 *                                  or equal to {@code value}
	 */
	S isLessThan(String name, T value);

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
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param name  the name of the maximum value
	 * @param value the maximum value
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is greater than
	 *                                  {@code value}
	 */
	S isLessThanOrEqualTo(String name, T value);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if {@code actual.compareTo(expected) != 0}
	 */
	S isComparableTo(T expected);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param name     the name of the expected value
	 * @param expected the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code actual.compareTo(expected) != 0}
	 */
	S isComparableTo(String name, T expected);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param other the other value
	 * @return this
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if {@code actual.compareTo(other) == 0}
	 */
	S isNotComparableTo(T other);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param name  the name of the other value
	 * @param other the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code other} are null
	 * @throws IllegalArgumentException if {@code actual.compareTo(other) == 0}
	 */
	S isNotComparableTo(String name, T other);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endExclusive   the upper bound of the range (exclusive)
	 * @return this
	 * @throws NullPointerException     if {@code startInclusive} or {@code endExclusive} are null
	 * @throws IllegalArgumentException if {@code endExclusive} is less than {@code startInclusive};
	 *                                  if the actual value is not in range
	 */
	S isBetween(T startInclusive, T endExclusive);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endInclusive   the upper bound of the range (inclusive)
	 * @return this
	 * @throws NullPointerException     if {@code startInclusive} or {@code endInclusive} are null
	 * @throws IllegalArgumentException if {@code endInclusive} is less than {@code startInclusive};
	 *                                  if the actual value is not in range
	 */
	S isBetweenClosed(T startInclusive, T endInclusive);
}
