/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of a {@code char} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveCharacterValidator extends Validator<PrimitiveCharacterValidator>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the validated value, or <code>&#92;u0000</code> if a validation failed
	 */
	@CheckReturnValue
	char getValue();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value in case of a validation failure
	 * @return the validated value, or {@code defaultValue} if a validation failed
	 */
	@CheckReturnValue
	char getValueOrDefault(char defaultValue);

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	PrimitiveCharacterValidator isEqualTo(char expected);

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is not equal to {@code expected}.
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	PrimitiveCharacterValidator isEqualTo(char expected, String name);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted}
	 */
	PrimitiveCharacterValidator isNotEqualTo(char unwanted);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @param name     the name of the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is equal to {@code unwanted}.
	 */
	PrimitiveCharacterValidator isNotEqualTo(char unwanted, String name);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	PrimitiveCharacterValidator isLessThan(char maximumExclusive);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @param name             the name of the upper bound
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains a leading, trailing whitespace or is empty. If
	 *                                  the value is greater than or equal to {@code maximumExclusive}.
	 */
	PrimitiveCharacterValidator isLessThan(char maximumExclusive, String name);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the maximum value
	 * @param name             the name of the maximum value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is greater than {@code maximumInclusive}.
	 */
	PrimitiveCharacterValidator isLessThanOrEqualTo(char maximumInclusive, String name);

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive);

	/**
	 * Ensures that the value is greater than or equal a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @param name             the name of the minimum value
	 * @return this
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is less than {@code minimumInclusive}.
	 */
	PrimitiveCharacterValidator isGreaterThanOrEqualTo(char minimumInclusive, String name);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws IllegalArgumentException if the value is less than or equal to {@code minimumExclusive}
	 */
	PrimitiveCharacterValidator isGreaterThan(char minimumExclusive);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @param name             the name of the lower bound
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is less than or equal to {@code minimumExclusive}.
	 */
	PrimitiveCharacterValidator isGreaterThan(char minimumExclusive, String name);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code startInclusive}. If the value is
	 *                                  greater than or equal to {@code endExclusive}.
	 */
	PrimitiveCharacterValidator isBetween(char minimumInclusive, char maximumExclusive);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum          the lower bound of the range
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the upper bound of the range
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code startInclusive}. If the value is
	 *                                  greater than {@code endInclusive}.
	 */
	PrimitiveCharacterValidator isBetween(char minimum, boolean minimumInclusive, char maximum,
		boolean maximumInclusive);
}