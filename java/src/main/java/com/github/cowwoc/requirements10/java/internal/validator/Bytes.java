package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.ConfigurationUpdater;
import com.github.cowwoc.requirements10.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements10.java.internal.message.ValidatorMessages;
import com.github.cowwoc.requirements10.java.internal.util.Numbers;

/**
 * Helper methods for validating {@code Byte}.
 *
 * @param <S> the type of validator that the methods should return
 */
final class Bytes<S>
{
	private final AbstractValidator<S, Byte> validator;
	private final Comparables<S, Byte> comparables;

	/**
	 * @param validator the validator to wrap
	 * @throws AssertionError if {@code validator} is null
	 */
	public Bytes(AbstractValidator<S, Byte> validator)
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
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(byte expected)
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
	 *                                    <li>the value is not equal to {@code expected}</li>
	 *                                  </ul>
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	public S isEqualTo(byte expected, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	private S isEqualToImpl(byte expected, String name)
	{
		if (validator.value.validationFailed(v -> v == expected))
		{
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
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted}
	 */
	public S isNotEqualTo(byte unwanted)
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
	 *                                    <li>the value is equal to {@code unwanted}</li>
	 *                                  </ul>
	 */
	public S isNotEqualTo(byte unwanted, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private S isNotEqualToImpl(byte unwanted, String name)
	{
		if (validator.value.validationFailed(v -> v != unwanted))
		{
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
	 * @throws IllegalArgumentException if the value is not negative
	 */
	public S isNegative()
	{
		if (validator.value.validationFailed(v -> v < (byte) 0))
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
		if (validator.value.validationFailed(v -> !(v < (byte) 0)))
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
	 * @throws IllegalArgumentException if the value is not zero
	 */
	public S isZero()
	{
		if (validator.value.validationFailed(v -> v == (byte) 0))
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
	@SuppressWarnings("PMD.LogicInversion")
	public S isNotZero()
	{
		if (validator.value.validationFailed(v -> !(v == (byte) 0)))
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
	 * @throws IllegalArgumentException if the value is not positive
	 */
	public S isPositive()
	{
		if (validator.value.validationFailed(v -> v > (byte) 0))
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
		if (validator.value.validationFailed(v -> !(v > (byte) 0)))
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
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	public S isLessThan(byte maximumExclusive)
	{
		return isLessThan((Byte) maximumExclusive);
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
	 *                                  </ul>
	 */
	public S isLessThan(byte maximumExclusive, String name)
	{
		return isLessThan((Byte) maximumExclusive, name);
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	public S isLessThanOrEqualTo(byte maximumInclusive)
	{
		return isLessThanOrEqualTo((Byte) maximumInclusive);
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
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(byte maximumInclusive, String name)
	{
		return isLessThanOrEqualTo((Byte) maximumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	public S isGreaterThanOrEqualTo(byte minimumInclusive)
	{
		return isGreaterThanOrEqualTo((Byte) minimumInclusive);
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
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(byte minimumInclusive, String name)
	{
		return isGreaterThanOrEqualTo((Byte) minimumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws IllegalArgumentException if the value is less than or equal to {@code minimumExclusive}
	 */
	public S isGreaterThan(byte minimumExclusive)
	{
		return isGreaterThan((Byte) minimumExclusive);
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
	 *                                  </ul>
	 */
	public S isGreaterThan(byte minimumExclusive, String name)
	{
		return isGreaterThan((Byte) minimumExclusive, name);
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
	 *                                  </ul>
	 */
	public S isBetween(byte minimumInclusive, byte maximumExclusive)
	{
		return isBetween((Byte) minimumInclusive, (Byte) maximumExclusive);
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
	 *                                    greater than or equal to {@code maximum}</li>
	 *                                  </ul>
	 */
	public S isBetween(byte minimum, boolean minimumIsInclusive, byte maximum, boolean maximumIsInclusive)
	{
		return isBetween((Byte) minimum, minimumIsInclusive, (Byte) maximum, maximumIsInclusive);
	}

	/**
	 * Ensures that the value is less than an upper bound.
	 *
	 * @param maximumExclusive the exclusive upper bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumExclusive} are null
	 * @throws IllegalArgumentException if the value is greater than or equal to {@code maximumExclusive}
	 */
	public S isLessThan(Byte maximumExclusive)
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
	 *                                  </ul>
	 */
	public S isLessThan(Byte maximumExclusive, String name)
	{
		return comparables.isLessThan(maximumExclusive, name);
	}

	/**
	 * Ensures that the value is less than or equal to a maximum value.
	 *
	 * @param maximumInclusive the inclusive upper value
	 * @return this
	 * @throws NullPointerException     if the value or {@code maximumInclusive} are null
	 * @throws IllegalArgumentException if the value is greater than {@code maximumInclusive}
	 */
	public S isLessThanOrEqualTo(Byte maximumInclusive)
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
	 *                                  </ul>
	 */
	public S isLessThanOrEqualTo(Byte maximumInclusive, String name)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than or equal to a minimum value.
	 *
	 * @param minimumInclusive the minimum value
	 * @return this
	 * @throws NullPointerException     if value or {@code minimumInclusive} are null
	 * @throws IllegalArgumentException if the value is less than {@code minimumInclusive}
	 */
	public S isGreaterThanOrEqualTo(Byte minimumInclusive)
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
	 *                                  </ul>
	 */
	public S isGreaterThanOrEqualTo(Byte minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	/**
	 * Ensures that the value is greater than a lower bound.
	 *
	 * @param minimumExclusive the exclusive lower bound
	 * @return this
	 * @throws NullPointerException     if the value or {@code minimumExclusive} are null
	 * @throws IllegalArgumentException if the value is less than or equal to {@code minimumExclusive}
	 */
	public S isGreaterThan(Byte minimumExclusive)
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
	 *                                  </ul>
	 */
	public S isGreaterThan(Byte minimumExclusive, String name)
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
	 *                                  </ul>
	 */
	public S isBetween(Byte minimumInclusive, Byte maximumExclusive)
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
	 *                                    greater than or equal to {@code maximum}</li>
	 *                                  </ul>
	 */
	public S isBetween(Byte minimum, boolean minimumIsInclusive, Byte maximum, boolean maximumIsInclusive)
	{
		return comparables.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	public S isMultipleOf(byte factor)
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
	 *                                  </ul>
	 */
	public S isMultipleOf(byte factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, name);
	}

	private S isMultipleOfImpl(byte factor, String name)
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
	public S isNotMultipleOf(byte factor)
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
	public S isNotMultipleOf(byte factor, String name)
	{
		validator.requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, name);
	}

	private S isNotMultipleOfImpl(byte factor, String name)
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
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	private S self()
	{
		return (S) validator;
	}
}