/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements10.java.validator.component.DecimalNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.FixedPrecisionDecimalNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.NegativeNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.PositiveNumberComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements10.java.validator.component.ZeroNumberComponent;

/**
 * Validates the state of a {@code Double}.
 */
public interface DoubleValidator extends
	ValidatorComponent<DoubleValidator, Double>,
	ObjectComponent<DoubleValidator, Double>,
	NegativeNumberComponent<DoubleValidator>,
	ZeroNumberComponent<DoubleValidator>,
	PositiveNumberComponent<DoubleValidator>,
	FixedPrecisionDecimalNumberComponent<DoubleValidator>,
	DecimalNumberComponent<DoubleValidator>,
	ComparableComponent<DoubleValidator, Double>
{
	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not a multiple of {@code factor}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isMultipleOf(double factor);

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not a multiple of {@code factor}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isMultipleOf(double factor, String name);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	DoubleValidator isNotMultipleOf(double factor);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is a multiple of {@code factor}</li>
	 *                                  </ul>
	 */
	DoubleValidator isNotMultipleOf(double factor, String name);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than or equal to {@code maximumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isLessThan(double maximumExclusive);

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @param name             the name of the upper bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than or equal to {@code maximumExclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isLessThan(double maximumExclusive, String name);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than {@code maximumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isLessThanOrEqualTo(double maximumInclusive);

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the maximum value
	 * @param name             the name of the maximum value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than {@code maximumInclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isLessThanOrEqualTo(double maximumInclusive, String name);

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than {@code minimumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive);

	/**
	 * Ensures that the value is greater than or equal a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @param name             the name of the minimum value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less than {@code minimumInclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isGreaterThanOrEqualTo(double minimumInclusive, String name);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than or equal to {@code minimumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isGreaterThan(double minimumExclusive);

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @param name             the name of the lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less or equal to {@code minimumExclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isGreaterThan(double minimumExclusive, String name);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimumInclusive} is greater than {@code maximumExclusive}</li>
	 *                                    <li>the value is greater than or equal to {@code maximumExclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isBetween(double minimumInclusive, double maximumExclusive);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum            the lower bound of the range
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the upper bound of the range
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimum} is greater than {@code maximum}</li>
	 *                                    <li>{@code minimumIsInclusive} is {@code true} and the value is less
	 *                                    than {@code minimum}</li>
	 *                                    <li>{@code minimumIsInclusive} is {@code false} and the value is less
	 *                                    than or equal to {@code minimum}</li>
	 *                                    <li>{@code maximumIsInclusive} is {@code true} and the value is
	 *                                    greater than {@code maximum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code false} and the value is
	 *                                    greater than or equal to {@code maximum}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	DoubleValidator isBetween(double minimum, boolean minimumIsInclusive, double maximum,
		boolean maximumIsInclusive);
}