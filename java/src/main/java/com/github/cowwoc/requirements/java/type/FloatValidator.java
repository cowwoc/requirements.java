/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.DecimalNumber;
import com.github.cowwoc.requirements.java.type.part.FixedPrecisionDecimalNumber;
import com.github.cowwoc.requirements.java.type.part.NegativeNumber;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.UnsignedNumber;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of a {@code Float} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface FloatValidator extends
	Validator<FloatValidator>,
	ObjectPart<FloatValidator, Float>,
	NegativeNumber<FloatValidator>,
	UnsignedNumber<FloatValidator>,
	FixedPrecisionDecimalNumber<FloatValidator>,
	DecimalNumber<FloatValidator>
{
	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	FloatValidator isMultipleOf(float factor);

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
	FloatValidator isMultipleOf(float factor, String name);

	/**
	 * Ensures that the value is not a multiple {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	FloatValidator isNotMultipleOf(float factor);

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
	FloatValidator isNotMultipleOf(float factor, String name);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	FloatValidator isLessThan(float maximumExclusive);

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
	FloatValidator isLessThan(float maximumExclusive, String name);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	FloatValidator isLessThanOrEqualTo(float maximumInclusive);

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
	FloatValidator isLessThanOrEqualTo(float maximumInclusive, String name);

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	FloatValidator isGreaterThanOrEqualTo(float minimumInclusive);

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
	FloatValidator isGreaterThanOrEqualTo(float minimumInclusive, String name);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is less than {@code minimumExclusive}
	 */
	FloatValidator isGreaterThan(float minimumExclusive);

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
	FloatValidator isGreaterThan(float minimumExclusive, String name);

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
	FloatValidator isBetween(float minimumInclusive, float maximumExclusive);

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
	FloatValidator isBetween(float minimum, boolean minimumInclusive, float maximum, boolean maximumInclusive);
}