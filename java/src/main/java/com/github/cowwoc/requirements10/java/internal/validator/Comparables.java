/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.message.ComparableMessages;

/**
 * Helper methods for validating {@code Comparable}.
 *
 * @param <S> the type of validator that the methods should return
 * @param <T> the type of the value that is being validated
 */
final class Comparables<S, T extends Comparable<T>>
{
	private final AbstractValidator<S, T> validator;

	/**
	 * @param validator the validator to wrap
	 * @throws AssertionError if {@code validator} is null
	 */
	public Comparables(AbstractValidator<S, T> validator)
	{
		assert validator != null;
		this.validator = validator;
	}

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumExclusive} are null
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	public S isLessThan(T maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @param name             the name of the upper bound
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than or equal to
	 *                                    {@code maximumExclusive}</li>
	 *                                  </ul>
	 */
	public S isLessThan(T maximumExclusive, String name)
	{
		validator.requireThatNameIsUnique(name).
			requireThat(maximumExclusive, "maximumExclusive").isNotNull();
		return isLessThanImpl(maximumExclusive, name);
	}

	private S isLessThanImpl(T maximumExclusive, String name)
	{
		if (validator.value.validationFailed(v -> v.compareTo(maximumExclusive) < 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ComparableMessages.isLessThanFailed(validator, name, maximumExclusive).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumInclusive} are null
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	public S isLessThanOrEqualTo(T maximumInclusive)
	{
		validator.scope.getInternalValidators().requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the maximum value
	 * @param name             the name of the maximum value
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than {@code maximumInclusive}</li>
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(T maximumInclusive, String name)
	{
		validator.requireThatNameIsUnique(name).
			requireThat(maximumInclusive, "maximumInclusive").isNotNull();
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private S isLessThanOrEqualToImpl(T maximumInclusive, String name)
	{
		if (validator.value.validationFailed(v -> v.compareTo(maximumInclusive) <= 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualToFailed(validator, name, maximumInclusive).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if value or {@code minimumInclusive} are null
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	public S isGreaterThanOrEqualTo(T minimumInclusive)
	{
		validator.scope.getInternalValidators().requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	/**
	 * Ensures that the value is greater than or equal a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @param name             the name of the minimum value
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less than {@code minimumInclusive}</li>
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(T minimumInclusive, String name)
	{
		validator.requireThatNameIsUnique(name).
			requireThat(minimumInclusive, "minimumInclusive").isNotNull();
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private S isGreaterThanOrEqualToImpl(T minimumInclusive, String name)
	{
		if (validator.value.validationFailed(v -> v.compareTo(minimumInclusive) >= 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualToFailed(validator, name, minimumInclusive).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code minimumExclusive} are null
	 * @throws IllegalArgumentException if the value is less than or equal to {@code minimumExclusive}
	 */
	public S isGreaterThan(T minimumExclusive)
	{
		validator.scope.getInternalValidators().requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, null);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @param name             the name of the lower bound
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is less than or equal to {@code minimumInclusive}</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(T minimumExclusive, String name)
	{
		validator.requireThatNameIsUnique(name).
			requireThat(minimumExclusive, "minimumExclusive").isNotNull();
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private S isGreaterThanImpl(T minimumExclusive, String name)
	{
		if (validator.value.validationFailed(v -> v.compareTo(minimumExclusive) > 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ComparableMessages.isGreaterThanFailed(validator, name, minimumExclusive).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is greater than or equal to
	 *                                    {@code maximumExclusive}</li>
	 *                                  </ul>
	 */
	public S isBetween(T minimumInclusive, T maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum            the lower bound of the range
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the upper bound of the range
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimum} is greater than {@code maximum}</li>
	 *                                    <li>{@code minimumIsInclusive} is {@code true} and the value is less
	 *                                    than {@code minimum}</li>
	 *                                    <li>{@code minimumIsInclusive} is {@code false} and the value is less
	 *                                    than or equal to {@code minimum}</li>
	 *                                    <li>{@code maximumIsInclusive} is {@code true} and the value is
	 *                                    greater than {@code maximum}</li>
	 *                                    <li>{@code maximumIsInclusive} is {@code false} and the value is
	 *                                    greater than or equal to {@code maximum}</li>
	 *                                  </ul>
	 */
	public S isBetween(T minimum, boolean minimumIsInclusive, T maximum, boolean maximumIsInclusive)
	{
		validator.scope.getInternalValidators().requireThat(minimum, "minimum").
			isLessThanOrEqualTo(maximum, "maximum");
		if (validator.value.validationFailed(v ->
			inBounds(v, minimum, minimumIsInclusive, maximum, maximumIsInclusive)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ComparableMessages.isBetweenFailed(validator, minimum, minimumIsInclusive, maximum,
						maximumIsInclusive).
					toString());
		}
		return self();
	}

	/**
	 * @param value              the value being validated
	 * @param minimum            the lower bound of the range
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the upper bound of the range
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @return true if the value is in bounds; false otherwise
	 */
	public boolean inBounds(T value, T minimum, boolean minimumIsInclusive, T maximum,
		boolean maximumIsInclusive)
	{
		if (minimumIsInclusive)
		{
			if (value.compareTo(minimum) < 0)
				return false;
		}
		else if (value.compareTo(minimum) <= 0)
			return false;
		if (maximumIsInclusive)
			return value.compareTo(maximum) <= 0;
		return value.compareTo(maximum) < 0;
	}

	/**
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	private S self()
	{
		return (S) validator;
	}
}