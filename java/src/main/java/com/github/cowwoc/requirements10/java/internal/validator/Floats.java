package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.message.ComparableMessages;
import com.github.cowwoc.requirements10.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements10.java.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.internal.util.Numbers;

/**
 * Helper methods for validating {@code Float}.
 *
 * @param <S> the type of validator that the methods should return
 */
final class Floats<S>
{
	private final AbstractValidator<S, Float> validator;
	private final Comparables<S, Float> comparables;

	/**
	 * @param validator the validator to wrap
	 * @throws AssertionError if {@code validator} is null
	 */
	public Floats(AbstractValidator<S, Float> validator)
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
	 *                                  {@link Float#equals(Object)}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(float expected)
	{
		return isEqualToImpl(expected, MaybeUndefined.undefined());
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
	 *                                    {@link Float#equals(Object)}</li>
	 *                                  </ul>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(float expected, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isEqualToImpl(expected, MaybeUndefined.defined(name));
	}

	private S isEqualToImpl(float expected, MaybeUndefined<String> name)
	{
		switch (validator.value.test(value -> value != null && Float.compare(value, expected) == 0))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				ComparableMessages.isEqualTo(validator.scope, validator, name, expected).toString());
		}
		return self();
	}

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted} per
	 *                                  {@link Float#equals(Object)}
	 */
	public S isNotEqualTo(float unwanted)
	{
		return isNotEqualToImpl(unwanted, MaybeUndefined.undefined());
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
	 *                                    {@link Float#equals(Object)}</li>
	 *                                  </ul>
	 */
	public S isNotEqualTo(float unwanted, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, MaybeUndefined.defined(name));
	}

	private S isNotEqualToImpl(float unwanted, MaybeUndefined<String> name)
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> Float.compare(value, unwanted) != 0))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				ComparableMessages.isNotEqualTo(validator.scope, validator, name, unwanted).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> Float.compare(value, 0f) < 0))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNegative(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !(Float.compare(value, 0f) < 0)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotNegative(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> Float.compare(value, -0f) == 0 ||
			Float.compare(value, 0f) == 0))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isZero(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !(Float.compare(value, -0f) == 0 ||
			Float.compare(value, 0f) == 0)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotZero(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !Double.isNaN(value) && Float.compare(value, 0f) > 0))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isPositive(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> Float.isNaN(value) || !(Float.compare(value, 0f) > 0)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotPositive(validator.scope, validator).toString());
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
	public S isLessThan(float maximumExclusive)
	{
		return isLessThan((Float) maximumExclusive);
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
	public S isLessThan(float maximumExclusive, String name)
	{
		return isLessThan((Float) maximumExclusive, name);
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
	public S isLessThanOrEqualTo(float maximumInclusive)
	{
		return isLessThanOrEqualTo((Float) maximumInclusive);
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
	public S isLessThanOrEqualTo(float maximumInclusive, String name)
	{
		return isLessThanOrEqualTo((Float) maximumInclusive, name);
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
	public S isGreaterThanOrEqualTo(float minimumInclusive)
	{
		return isGreaterThanOrEqualTo((Float) minimumInclusive);
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
	public S isGreaterThanOrEqualTo(float minimumInclusive, String name)
	{
		return isGreaterThanOrEqualTo((Float) minimumInclusive, name);
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
	public S isGreaterThan(float minimumExclusive)
	{
		return isGreaterThan((Float) minimumExclusive);
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
	public S isGreaterThan(float minimumExclusive, String name)
	{
		return isGreaterThan((Float) minimumExclusive, name);
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
	public S isBetween(float minimumInclusive, float maximumExclusive)
	{
		return isBetween((Float) minimumInclusive, (Float) maximumExclusive);
	}

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
	 *                                    <li>{@code minimumInclusive} is {@code true} and the value is less
	 *                                    than {@code minimum}</li>
	 *                                    <li>{@code minimumInclusive} is {@code false} and the value is less
	 *                                    than or equal to {@code minimum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code true} and the value is greater
	 *                                    than {@code maximum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code false} and the value is greater
	 *                                    than or equal to {@code maximum}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(float minimum, boolean minimumInclusive, float maximum,
		boolean maximumInclusive)
	{
		return isBetween((Float) minimum, minimumInclusive, (Float) maximum, maximumInclusive);
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
	public S isLessThan(Float maximumExclusive)
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
	public S isLessThan(Float maximumExclusive, String name)
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
	public S isLessThanOrEqualTo(Float maximumInclusive)
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
	public S isLessThanOrEqualTo(Float maximumInclusive, String name)
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
	public S isGreaterThanOrEqualTo(Float minimumInclusive)
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
	public S isGreaterThanOrEqualTo(Float minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code minimumExclusive} is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>less than or equal to {@code minimumExclusive}</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	public S isGreaterThan(Float minimumExclusive)
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
	public S isGreaterThan(Float minimumExclusive, String name)
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
	public S isBetween(Float minimumInclusive, Float maximumExclusive)
	{
		return comparables.isBetween(minimumInclusive, maximumExclusive);
	}

	/**
	 * Ensures that the value is within a range.
	 *
	 * @param minimum          the lower bound of the range
	 * @param minimumInclusive {@code true} if the lower bound of the range is inclusive
	 * @param maximum          the upper bound of the range
	 * @param maximumInclusive {@code true} if the upper bound of the range is inclusive
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code minimum} is greater than {@code maximum}</li>
	 *                                    <li>{@code minimumInclusive} is {@code true} and the value is less
	 *                                    than {@code minimum}</li>
	 *                                    <li>{@code minimumInclusive} is {@code false} and the value is less
	 *                                    than or equal to {@code minimum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code true} and the value is greater
	 *                                    than {@code maximum}</li>
	 *                                    <li>{@code maximumInclusive} is {@code false} and the value is greater
	 *                                    than or equal to {@code maximum}</li>
	 *                                    <li>the value is not a number</li>
	 *                                  </ul>
	 */
	public S isBetween(Float minimum, boolean minimumInclusive, Float maximum, boolean maximumInclusive)
	{
		return comparables.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
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
	public S isMultipleOf(float factor)
	{
		return isMultipleOfImpl(factor, MaybeUndefined.undefined());
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
	public S isMultipleOf(float factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private S isMultipleOfImpl(float factor, MaybeUndefined<String> name)
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> Numbers.isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isMultipleOf(validator.scope, validator, name, factor).toString());
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
	public S isNotMultipleOf(float factor)
	{
		return isNotMultipleOfImpl(factor, MaybeUndefined.undefined());
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
	public S isNotMultipleOf(float factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private S isNotMultipleOfImpl(float factor, MaybeUndefined<String> name)
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !Numbers.isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(validator.scope, validator, name, factor).toString());
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
	 * @see Float#isNaN()
	 */
	public S isNumber()
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !value.isNaN()))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNumber(validator.scope, validator).toString());
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
	 * @see Float#isNaN()
	 */
	public S isNotNumber()
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> value.isNaN()))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotNumber(validator.scope, validator).toString());
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
	 * @see Float#isInfinite()
	 */
	public S isFinite()
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(Float::isFinite))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isFinite(validator.scope, validator).toString());
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
	 * @see Float#isInfinite()
	 */
	public S isInfinite()
	{
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> value.isInfinite()))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isInfinite(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(Numbers::isWholeNumber))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isWholeNumber(validator.scope, validator).toString());
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
		if (validator.value.isNull())
			validator.onNull();
		switch (validator.value.test(value -> !Numbers.isWholeNumber(value)))
		{
			case UNDEFINED, FALSE -> validator.addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(validator.scope, validator).toString());
		}
		return self();
	}

	/**
	 * @return this
	 */
	private S self()
	{
		@SuppressWarnings("unchecked")
		S self = (S) validator;
		return self;
	}
}