/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.ComparableValidator;

/**
 * Validates the requirements of a value that extends {@link Comparable} but the implementing validator is
 * not guaranteed to be a {@link ComparableValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public interface ExtensibleComparableValidator<S, T extends Comparable<? super T>>
	extends ExtensibleObjectValidator<S, T>
{
	/**
	 * Ensures that the actual value is greater than the specified value.
	 *
	 * @param value a lower bound
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isGreaterThan(T value);

	/**
	 * Ensures that the actual value is greater than the specified the specified value.
	 *
	 * @param value a lower bound
	 * @param name  the name of the lower bound
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isGreaterThan(T value, String name);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isGreaterThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is greater than or equal to the specified value.
	 *
	 * @param value the minimum value
	 * @param name  the name of the minimum value
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} or {@code value} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isGreaterThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is less than the specified value.
	 *
	 * @param value the upper bound
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isLessThan(T value);

	/**
	 * Ensures that the actual value is less than the value of the specified value.
	 *
	 * @param value the upper bound
	 * @param name  the name of the upper bound
	 * @return the updated validator
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isLessThan(T value, String name);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isLessThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is less than or equal to the specified value.
	 *
	 * @param value the maximum value
	 * @param name  the name of the maximum value
	 * @return the updated validator
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isLessThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param expected the expected value
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isComparableTo(T expected);

	/**
	 * Ensures that the actual value is comparable to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isComparableTo(T expected, String name);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param other the other value
	 * @return the updated validator
	 * @throws NullPointerException if {@code value} is null
	 */
	S isNotComparableTo(T other);

	/**
	 * Ensures that the actual value is not comparable to another value.
	 *
	 * @param other the other value
	 * @param name  the name of the other value
	 * @return the updated validator
	 * @throws NullPointerException     if {@code other} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S isNotComparableTo(T other, String name);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endExclusive   the upper bound of the range (exclusive)
	 * @return the updated validator
	 * @throws NullPointerException if {@code startInclusive} or {@code endExclusive} are null
	 */
	S isBetween(T startInclusive, T endExclusive);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param startInclusive the lower bound of the range (inclusive)
	 * @param endInclusive   the upper bound of the range (inclusive)
	 * @return the updated validator
	 * @throws NullPointerException if {@code startInclusive} or {@code endInclusive} are null
	 */
	S isBetweenClosed(T startInclusive, T endInclusive);
}