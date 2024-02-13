package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.ByteValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ByteValidatorImpl extends AbstractObjectValidator<ByteValidator, Byte>
	implements ByteValidator
{
	/**
	 * Creates a new validator as a result of a validation.
	 *
	 * @param scope     the application configuration
	 * @param validator the validator
	 * @param name      the name of the value
	 * @param value     (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ByteValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Byte value)
	{
		this(scope, validator.configuration(), name, value, validator.context, validator.failures);
	}

	/**
	 * Creates a new validator.
	 *
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	public ByteValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Byte value)
	{
		this(scope, configuration, name, value, HashMap.newHashMap(2), new ArrayList<>(1));
	}

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         (optional) the value
	 * @param context       the contextual information set by the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty
	 * @throws AssertionError           if any of the mandatory arguments are null. If {@code name} contains
	 *                                  leading or trailing whitespace, or is empty.
	 */
	private ByteValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Byte value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(byte number, byte factor)
	{
		return factor != (byte) 0 && (number == (byte) 0 || (number % factor == 0));
	}

	@Override
	public ByteValidator isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value >= 0)
		{
			addIllegalArgumentException(NumberMessages.isNegative(scope, this, this.name,
				value).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value < 0)
		{
			addIllegalArgumentException(
				(NumberMessages.isNotNegative(scope, this, this.name, value).toString()));
		}
		return this;
	}

	@Override
	public ByteValidator isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value != 0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value == 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value <= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value > 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isMultipleOf(byte factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public ByteValidator isMultipleOf(byte factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isMultipleOfImpl(factor, name);
	}

	private ByteValidator isMultipleOfImpl(byte factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isNotMultipleOf(byte factor)
	{
		return isNotMultipleOfImpl(null, factor);
	}

	@Override
	public ByteValidator isNotMultipleOf(byte factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isNotMultipleOfImpl(name, factor);
	}

	private ByteValidator isNotMultipleOfImpl(String name, byte factor)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (isMultipleOf(value, factor))
		{
			addIllegalArgumentException(NumberMessages.isNotMultipleOf(scope, this, this.name,
				value, name, factor).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isLessThan(byte maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public ByteValidator isLessThan(byte maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isLessThanImpl(maximumExclusive, name);
	}

	private ByteValidator isLessThanImpl(byte maximumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(maximumExclusive) >= 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public ByteValidator isLessThanOrEqualTo(byte maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public ByteValidator isLessThanOrEqualTo(byte maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private ByteValidator isLessThanOrEqualToImpl(byte maximumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name,
					maximumInclusive).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(maximumInclusive) > 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name,
					maximumInclusive).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isGreaterThanOrEqualTo(byte minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public ByteValidator isGreaterThanOrEqualTo(byte minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private ByteValidator isGreaterThanOrEqualToImpl(byte minimumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name,
					minimumInclusive).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(minimumInclusive) < 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name,
					minimumInclusive).toString());
		}
		return this;
	}

	@Override
	public ByteValidator isGreaterThan(byte minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public ByteValidator isGreaterThan(byte minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		return isGreaterThanImpl(minimumExclusive, name);
	}

	private ByteValidator isGreaterThanImpl(byte minimumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, null, name, minimumExclusive).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(minimumExclusive) <= 0)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public ByteValidator isBetween(byte minimumInclusive, byte maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public ByteValidator isBetween(byte minimum, boolean minimumInclusive, byte maximum,
		boolean maximumInclusive)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(byte minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value.compareTo(minimum) < 0;
		return value.compareTo(minimum) <= 0;
	}

	private boolean maximumFailed(byte maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value.compareTo(maximum) > 0;
		return value.compareTo(maximum) >= 0;
	}
}