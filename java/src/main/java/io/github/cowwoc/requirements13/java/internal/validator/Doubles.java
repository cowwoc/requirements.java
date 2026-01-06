/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.internal.ConfigurationUpdater;
import io.github.cowwoc.requirements13.java.internal.message.NumberMessages;
import io.github.cowwoc.requirements13.java.internal.message.ValidatorMessages;
import io.github.cowwoc.requirements13.java.internal.util.Numbers;

/**
 * Helper methods for validating {@code Double}.
 *
 * @param <S> the type of validator that the methods should return
 */
final class Doubles<S>
{
	private final AbstractValidator<S, Double> validator;
	private final Comparables<S, Double> comparables;

	/**
	 * @param validator the validator to wrap
	 * @throws AssertionError if {@code validator} is null
	 */
	public Doubles(AbstractValidator<S, Double> validator)
	{
		assert validator != null;
		this.validator = validator;
		this.comparables = new Comparables<>(validator);
	}

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected} per
	 *                                  {@link Double#equals(Object)}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(double expected)
	{
		return isEqualToImpl(expected, null);
	}

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
	 *                                    <li>the value is not equal to {@code expected} per
	 *                                    {@link Double#equals(Object)}</li>
	 *                                  </ul>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(double expected, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	private S isEqualToImpl(double expected, String name)
	{
		if (validator.value.validationFailed(v -> Double.compare(v, expected) == 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ValidatorMessages.isEqualToFailed(validator, name, expected).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted} per
	 *                                  {@link Double#equals(Object)}
	 */
	public S isNotEqualTo(double unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

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
	 *                                    <li>the value is equal to {@code unwanted} per
	 *                                    {@link Double#equals(Object)}</li>
	 *                                  </ul>
	 */
	public S isNotEqualTo(double unwanted, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private S isNotEqualToImpl(double unwanted, String name)
	{
		if (validator.value.validationFailed(v -> Double.compare(v, unwanted) != 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				ValidatorMessages.isNotEqualToFailed(validator, name, unwanted).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is negative. {@code -0.0} is considered to be equal to zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not negative</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isNegative()
	{
		if (validator.value.validationFailed(v -> Double.compare(v, 0.0) < 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNegativeFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not negative. {@code -0.0} is considered to be equal to zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is negative
	 */
	@SuppressWarnings("PMD.LogicInversion")
	public S isNotNegative()
	{
		if (validator.value.validationFailed(v -> !(Double.compare(v, 0.0) < 0)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotNegativeFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is zero. {@code -0.0} is considered to be equal to zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not zero</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isZero()
	{
		if (validator.value.validationFailed(v -> Double.compare(v, -0.0) == 0 || Double.compare(v, 0.0) == 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isZeroFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not zero. {@code -0.0} is considered to be equal to zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is zero
	 */
	public S isNotZero()
	{
		if (validator.value.validationFailed(v -> !(Double.compare(v, -0.0) == 0 || Double.compare(v, 0.0) == 0)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotZeroFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is positive.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not positive</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isPositive()
	{
		if (validator.value.validationFailed(v -> !v.isNaN() && Double.compare(v, 0.0) > 0))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isPositiveFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not positive.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is positive
	 */
	@SuppressWarnings("PMD.LogicInversion")
	public S isNotPositive()
	{
		if (validator.value.validationFailed(v -> v.isNaN() || !(Double.compare(v, 0.0) > 0)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotPositiveFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than or equal to {@code maximumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isLessThan(double maximumExclusive)
	{
		return isLessThan((Double) maximumExclusive);
	}

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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isLessThan(double maximumExclusive, String name)
	{
		return isLessThan((Double) maximumExclusive, name);
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than {@code maximumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(double maximumInclusive)
	{
		return isLessThanOrEqualTo((Double) maximumInclusive);
	}

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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(double maximumInclusive, String name)
	{
		return isLessThanOrEqualTo((Double) maximumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than {@code minimumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(double minimumInclusive)
	{
		return isGreaterThanOrEqualTo((Double) minimumInclusive);
	}

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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(double minimumInclusive, String name)
	{
		return isGreaterThanOrEqualTo((Double) minimumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than or equal to {@code minimumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(double minimumExclusive)
	{
		return isGreaterThan((Double) minimumExclusive);
	}

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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(double minimumExclusive, String name)
	{
		return isGreaterThan((Double) minimumExclusive, name);
	}

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimumInclusive the lower bound of the range (inclusive)
	 * @param maximumExclusive the upper bound of the range (exclusive)
	 * @return this
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimumInclusive} is greater than
	 *                                    {@code maximumExclusive}</li>
	 *                                    <li>the value is greater than or equal to
	 *                                    {@code maximumExclusive}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(double minimumInclusive, double maximumExclusive)
	{
		return isBetween((Double) minimumInclusive, (Double) maximumExclusive);
	}

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum            the lower bound of the range
	 * @param minimumIsInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum            the upper bound of the range
	 * @param maximumIsInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
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
	 *                                    greater than or equal to {@code maximum}</li>	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(double minimum, boolean minimumIsInclusive, double maximum, boolean maximumIsInclusive)
	{
		return isBetween((Double) minimum, minimumIsInclusive, (Double) maximum, maximumIsInclusive);
	}

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumExclusive} are null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than or equal to {@code maximumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isLessThan(Double maximumExclusive)
	{
		return comparables.isLessThan(maximumExclusive);
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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isLessThan(Double maximumExclusive, String name)
	{
		return comparables.isLessThan(maximumExclusive, name);
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumInclusive} are null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>greater than {@code maximumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(Double maximumInclusive)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive);
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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(Double maximumInclusive, String name)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if value or {@code minimumInclusive} are null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than {@code minimumInclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(Double minimumInclusive)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive);
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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(Double minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code minimumExclusive} are null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than or equal to {@code minimumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(Double minimumExclusive)
	{
		return comparables.isGreaterThan(minimumExclusive);
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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(Double minimumExclusive, String name)
	{
		return comparables.isGreaterThan(minimumExclusive, name);
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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(Double minimumInclusive, Double maximumExclusive)
	{
		return comparables.isBetween(minimumInclusive, maximumExclusive);
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
	 *                                    greater than or equal to {@code maximum}</li>	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(Double minimum, boolean minimumIsInclusive, Double maximum, boolean maximumIsInclusive)
	{
		return comparables.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not a multiple of {@code factor}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isMultipleOf(double factor)
	{
		return isMultipleOfImpl(factor, null);
	}

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
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isMultipleOf(double factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, name);
	}

	private S isMultipleOfImpl(double factor, String name)
	{
		if (validator.value.validationFailed(v -> Numbers.isMultipleOf(v, factor)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isMultipleOfFailed(validator, name, factor).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	public S isNotMultipleOf(double factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

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
	 *                                    <li>the value is a multiple of {@code factor}</li>
	 *                                  </ul>
	 */
	public S isNotMultipleOf(double factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, name);
	}

	private S isNotMultipleOfImpl(double factor, String name)
	{
		if (validator.value.validationFailed(v -> !Numbers.isMultipleOf(v, factor)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotMultipleOfFailed(validator, name, factor).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is a well-defined number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not a number
	 * @see #isNotNumber()
	 * @see Double#isNaN()
	 */
	public S isNumber()
	{
		if (validator.value.validationFailed(v -> !v.isNaN()))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNumberFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is the result of a mathematically undefined numerical operation (such as division
	 * by zero) or don't have a representation in real numbers (such as the square-root of -1).
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is a well-defined number
	 * @see Double#isNaN()
	 */
	public S isNotNumber()
	{
		if (validator.value.validationFailed(v -> v.isNaN()))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotNumberFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is a finite number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>an infinite number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 * @see #isNumber()
	 * @see Double#isInfinite()
	 */
	public S isFinite()
	{
		if (validator.value.validationFailed(Double::isFinite))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isFiniteFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is an infinite number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>a finite number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 * @see #isNumber()
	 * @see Double#isInfinite()
	 */
	public S isInfinite()
	{
		if (validator.value.validationFailed(v -> Double.isInfinite(v)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isInfiniteFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is a whole number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not a whole number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isWholeNumber()
	{
		if (validator.value.validationFailed(Numbers::isWholeNumber))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isWholeNumberFailed(validator).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not a whole number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is a whole number
	 */
	public S isNotWholeNumber()
	{
		if (validator.value.validationFailed(v -> !Numbers.isWholeNumber(v)))
		{
			validator.failOnNull();
			validator.addIllegalArgumentException(
				NumberMessages.isNotWholeNumberFailed(validator).toString());
		}
		return self();
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