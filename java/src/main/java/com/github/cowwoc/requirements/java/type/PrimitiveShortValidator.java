/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.NegativeNumber;
import com.github.cowwoc.requirements.java.type.part.UnsignedNumber;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of a {@code short} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveShortValidator extends
	Validator<PrimitiveShortValidator>,
	NegativeNumber<PrimitiveShortValidator>,
	UnsignedNumber<PrimitiveShortValidator>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the value that is being validated
	 */
	@CheckReturnValue
	short getValue();

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	PrimitiveShortValidator isEqualTo(short expected);

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
	PrimitiveShortValidator isEqualTo(short expected, String name);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted}
	 */
	PrimitiveShortValidator isNotEqualTo(short unwanted);

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
	PrimitiveShortValidator isNotEqualTo(short unwanted, String name);

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	PrimitiveShortValidator isMultipleOf(short factor);

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is not a multiple of {@code factor}.
	 */
	PrimitiveShortValidator isMultipleOf(short factor, String name);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	PrimitiveShortValidator isNotMultipleOf(short factor);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is a multiple of {@code factor}.
	 */
	PrimitiveShortValidator isNotMultipleOf(short factor, String name);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	PrimitiveShortValidator isLessThan(short maximumExclusive);

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
	PrimitiveShortValidator isLessThan(short maximumExclusive, String name);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive);

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
	PrimitiveShortValidator isLessThanOrEqualTo(short maximumInclusive, String name);

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive);

	/**
	 * Ensures that the value is greater than or equal a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @param name             the name of the minimum value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is less than {@code minimumInclusive}.
	 */
	PrimitiveShortValidator isGreaterThanOrEqualTo(short minimumInclusive, String name);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws IllegalArgumentException if the value is less than or equal to {@code minimumExclusive}
	 */
	PrimitiveShortValidator isGreaterThan(short minimumExclusive);

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
	PrimitiveShortValidator isGreaterThan(short minimumExclusive, String name);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code startInclusive}. If the value is
	 *                                  greater than or equal to {@code endExclusive}.
	 */
	PrimitiveShortValidator isBetween(short minimumInclusive, short maximumExclusive);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum          the lower bound of the range
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the upper bound of the range
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws IllegalArgumentException if {@code minimumInclusive} is {@code true} and the value is less than
	 *                                  {@code minimum}. If {@code minimumInclusive} is {@code false} and the
	 *                                  value is less than or equal to {@code minimum}. If
	 *                                  {@code maximumInclusive} is {@code true} and the value is greater than
	 *                                  {@code maximum}. If {@code maximumInclusive} is {@code false} and the
	 *                                  value is greater than or equal to {@code maximum}.
	 */
	PrimitiveShortValidator isBetween(short minimum, boolean minimumInclusive, short maximum,
		boolean maximumInclusive);
}