/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.validator;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements.java.validator.component.ZeroNumberComponent;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.validator.component.NegativeNumberComponent;
import com.github.cowwoc.requirements.java.validator.component.PositiveNumberComponent;

import java.util.function.Function;

/**
 * Validates the state of a {@code short}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveShortValidator extends
	ValidatorComponent<PrimitiveShortValidator, Short>,
	NegativeNumberComponent<PrimitiveShortValidator>,
	ZeroNumberComponent<PrimitiveShortValidator>,
	PositiveNumberComponent<PrimitiveShortValidator>,
	ComparableComponent<PrimitiveShortValidator, Short>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the value
	 * @throws IllegalStateException if a previous validation failed
	 */
	@CheckReturnValue
	short getValue();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value in case of a validation failure
	 * @return the value, or {@code defaultValue} if a previous validation failed
	 */
	@CheckReturnValue
	short getValueOrDefault(short defaultValue);

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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not equal to {@code expected}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is equal to {@code unwanted}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not a multiple of {@code factor}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>{@code value} is a multiple of {@code factor}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than or equal to {@code maximumExclusive}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than {@code maximumInclusive}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less than {@code minimumInclusive}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less or equal to {@code minimumExclusive}</li>
	 *                                  </ul>
	 */
	PrimitiveShortValidator isGreaterThan(short minimumExclusive, String name);

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimumInclusive} is greater than {@code maximumExclusive}</li>
	 *                                    <li>the value is greater than or equal to {@code maximumExclusive}</li>
	 *                                  </ul>
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
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimum} is greater than {@code maximum}</li>
	 *                                    <li>{@code minimumInclusive} is {@code true} and the value is less than {@code minimum}</li>
	 *                                    <li>{@code minimumInclusive} is {@code false} and the value is less than or equal to {@code minimum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code true} and the value is greater than {@code maximum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code false} and the value is greater than or equal to {@code maximum}</li>
	 *                                  </ul>
	 */
	PrimitiveShortValidator isBetween(short minimum, boolean minimumInclusive, short maximum,
		boolean maximumInclusive);
}