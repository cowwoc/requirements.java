/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.NegativeNumber;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.UnsignedNumber;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of an {@code Integer} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface IntegerValidator extends
	Validator<IntegerValidator>,
	ObjectPart<IntegerValidator, Integer>,
	NegativeNumber<IntegerValidator>,
	UnsignedNumber<IntegerValidator>
{
	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	IntegerValidator isMultipleOf(int factor);

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is not a multiple of {@code factor}.
	 */
	IntegerValidator isMultipleOf(int factor, String name);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	IntegerValidator isNotMultipleOf(int factor);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is a multiple of {@code factor}.
	 */
	IntegerValidator isNotMultipleOf(int factor, String name);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	IntegerValidator isLessThan(int maximumExclusive);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @param name             the name of the upper bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains a leading, trailing whitespace or is empty. If
	 *                                  the value is greater than or equal to {@code maximumExclusive}.
	 */
	IntegerValidator isLessThan(int maximumExclusive, String name);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	IntegerValidator isLessThanOrEqualTo(int maximumInclusive);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the maximum value
	 * @param name             the name of the maximum value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is greater than {@code maximumInclusive}.
	 */
	IntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name);

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	IntegerValidator isGreaterThanOrEqualTo(int minimumInclusive);

	/**
	 * Ensures that the value is greater than or equal a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @param name             the name of the minimum value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is less than {@code minimumInclusive}.
	 */
	IntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is less than {@code minimumExclusive}
	 */
	IntegerValidator isGreaterThan(int minimumExclusive);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @param name             the name of the lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is less than {@code minimumExclusive}.
	 */
	IntegerValidator isGreaterThan(int minimumExclusive, String name);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is less than {@code startInclusive}. If the value is
	 *                                  greater than or equal to {@code endExclusive}.
	 */
	IntegerValidator isBetween(int minimumInclusive, int maximumExclusive);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum          the lower bound of the range
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the upper bound of the range
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if {@code minimumInclusive} is {@code true} and the value is less than
	 *                                  {@code minimum}. If {@code minimumInclusive} is {@code false} and the
	 *                                  value is less than or equal to {@code minimum}. If
	 *                                  {@code maximumInclusive} is {@code true} and the value is greater than
	 *                                  {@code maximum}. If {@code maximumInclusive} is {@code false} and the
	 *                                  value is greater than or equal to {@code maximum}.
	 */
	IntegerValidator isBetween(int minimum, boolean minimumInclusive, int maximum, boolean maximumInclusive);
}