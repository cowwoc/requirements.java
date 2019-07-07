/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;

/**
 * Verifies the requirements of a value that extends {@link Comparable} but the implementing verifier is
 * not guaranteed to be a {@link ComparableVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public interface ExtensibleComparableVerifier<S, T extends Comparable<? super T>>
	extends ExtensibleObjectVerifier<S, T>
{
	/**
	 * Ensures that the actual value is greater than the specified value.
	 *
	 * @param value a lower bound
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value}
	 */
	S isGreaterThan(T value);

	/**
	 * Ensures that the actual value is greater than the specified the specified value.
	 *
	 * @param value a lower bound
	 * @param name  the name of the lower bound
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value}. If
	 *                                  {@code name} is empty.
	 */
	S isGreaterThan(T value, String name);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}
	 */
	S isGreaterThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @param name  the name of the minimum value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}. If {@code name}
	 *                                  is empty.
	 */
	S isGreaterThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is less than the specified value.
	 *
	 * @param value the upper bound
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to {@code value}
	 */
	S isLessThan(T value);

	/**
	 * Ensures that the actual value is less than the value of the specified value.
	 *
	 * @param value the upper bound
	 * @param name  the name of the upper bound
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to {@code value}. If
	 *                                  {@code name} is empty.
	 */
	S isLessThan(T value, String name);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}
	 */
	S isLessThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @param name  the name of the maximum value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}. If {@code name}
	 *                                  is empty.
	 */
	S isLessThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param expected the expected value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if {@code actual.compareTo(expected) != 0}
	 */
	S isComparableTo(T expected);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code actual.compareTo(expected) != 0}. If {@code name} is empty.
	 */
	S isComparableTo(T expected, String name);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param other the other value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if {@code actual.compareTo(other) == 0}
	 */
	S isNotComparableTo(T other);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param other the other value
	 * @param name  the name of the other value
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} or {@code name} are null
	 * @throws IllegalArgumentException if {@code actual.compareTo(other) == 0}. If {@code name} is empty.
	 */
	S isNotComparableTo(T other, String name);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endExclusive   the upper bound of the range (exclusive)
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code startInclusive} or {@code endExclusive} are null
	 * @throws IllegalArgumentException if {@code endExclusive} is less than {@code startInclusive}. If the
	 *                                  actual value is not in range.
	 */
	S isBetween(T startInclusive, T endExclusive);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endInclusive   the upper bound of the range (inclusive)
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code startInclusive} or {@code endInclusive} are null
	 * @throws IllegalArgumentException if {@code endInclusive} is less than {@code startInclusive}. If the
	 *                                  actual value is not in range.
	 */
	S isBetweenClosed(T startInclusive, T endInclusive);
}
