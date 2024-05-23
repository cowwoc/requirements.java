package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.EqualMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.FloatValidator;
import com.github.cowwoc.requirements.java.type.PrimitiveFloatValidator;

import java.util.List;
import java.util.Map;

public final class PrimitiveFloatValidatorImpl extends AbstractValidator<PrimitiveFloatValidator>
	implements PrimitiveFloatValidator
{
	private final float value;

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
	public PrimitiveFloatValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		float value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, context, failures);
		this.value = value;
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(double value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1) == 0;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(float number, float factor)
	{
		return factor != 0.0f && (number == 0.0f || (number % factor == 0));
	}

	@Override
	public float getValue()
	{
		return value;
	}

	@Override
	public float getValueOrDefault(float defaultValue)
	{
		if (hasFailed())
			return defaultValue;
		return value;
	}

	@Override
	public PrimitiveFloatValidator isEqualTo(float expected)
	{
		return isEqualToImpl(expected, null);
	}

	@Override
	public PrimitiveFloatValidator isEqualTo(float expected, String name)
	{
		requireThatNameIsUnique(name);
		return isEqualToImpl(expected, name);
	}

	private PrimitiveFloatValidator isEqualToImpl(float expected, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, null, false, name, expected).toString());
		}
		else if (value != expected)
		{
			addIllegalArgumentException(
				EqualMessages.isEqualTo(scope, this, this.name, value, true, name, expected).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotEqualTo(float unwanted)
	{
		return isNotEqualToImpl(unwanted, null);
	}

	@Override
	public PrimitiveFloatValidator isNotEqualTo(float unwanted, String name)
	{
		requireThatNameIsUnique(name);
		return isNotEqualToImpl(unwanted, name);
	}

	private PrimitiveFloatValidator isNotEqualToImpl(float unwanted, String name)
	{
		if (hasFailed() || value == unwanted)
		{
			addIllegalArgumentException(
				EqualMessages.isNotEqualTo(scope, this, this.name, name, unwanted).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		}
		else if (value >= 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotNegative()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, null).toString());
		}
		else if (value < 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, null).toString());
		}
		else if (value != 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotZero()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		else if (value == 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, null).toString());
		}
		else if (value <= 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotPositive()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, null).toString());
		}
		else if (value > 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, null).toString());
		}
		else if (Double.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, null).toString());
		}
		else if (!Double.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isFinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, null).toString());
		}
		else if (!Double.isFinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isInfinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, null).toString());
		}
		else if (!Double.isInfinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, null).toString());
		}
		else if (!isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, null).toString());
		}
		else if (isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isMultipleOf(float factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveFloatValidator isMultipleOf(float factor, String name)
	{
		requireThatNameIsUnique(name);
		return isMultipleOfImpl(factor, name);
	}

	private PrimitiveFloatValidator isMultipleOfImpl(float factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (!isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isNotMultipleOf(float factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public PrimitiveFloatValidator isNotMultipleOf(float factor, String name)
	{
		requireThatNameIsUnique(name);
		return isNotMultipleOfImpl(factor, name);
	}

	private PrimitiveFloatValidator isNotMultipleOfImpl(float factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, null, name, factor).toString());
		}
		else if (isMultipleOf(value, factor))
		{
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isLessThan(float maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public PrimitiveFloatValidator isLessThan(float maximumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanImpl(maximumExclusive, name);
	}

	private PrimitiveFloatValidator isLessThanImpl(float maximumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).
					toString());
		}
		else if (value >= maximumExclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(float maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public PrimitiveFloatValidator isLessThanOrEqualTo(float maximumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private PrimitiveFloatValidator isLessThanOrEqualToImpl(float maximumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, null, name,
					maximumInclusive).toString());
		}
		else if (value > maximumInclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThanOrEqualTo(scope, this, this.name, value, name,
					maximumInclusive).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(float minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThanOrEqualTo(float minimumInclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private PrimitiveFloatValidator isGreaterThanOrEqualToImpl(float minimumInclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, null, name,
					minimumInclusive).toString());
		}
		else if (value < minimumInclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThanOrEqualTo(scope, this, this.name, value, name,
					minimumInclusive).toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(float minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public PrimitiveFloatValidator isGreaterThan(float minimumExclusive, String name)
	{
		requireThatNameIsUnique(name);
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private PrimitiveFloatValidator isGreaterThanImpl(float minimumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, null, name, minimumExclusive).
					toString());
		}
		else if (value <= minimumExclusive)
		{
			addIllegalArgumentException(
				ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public PrimitiveFloatValidator isBetween(float minimumInclusive, float maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public PrimitiveFloatValidator isBetween(float minimum, boolean minimumInclusive, float maximum,
		boolean maximumInclusive)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, null, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		else if (minimumFailed(minimum, minimumInclusive) || maximumFailed(maximum, maximumInclusive))
		{
			addIllegalArgumentException(
				ComparableMessages.isBetween(scope, this, this.name, value, minimum, minimumInclusive,
					maximum, maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(float minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
		{
			return value < minimum;
		}
		return value <= minimum;
	}

	private boolean maximumFailed(float maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
		{
			return value > maximum;
		}
		return value >= maximum;
	}

	@Override
	public StringValidatorImpl asString()
	{
		return new StringValidatorImpl(scope, configuration, "String.valueOf(" + name + ")",
			String.valueOf(value), context, failures);
	}

	@Override
	public FloatValidator asBoxed()
	{
		if (hasFailed())
			return new FloatValidatorImpl(scope, configuration, name, null, context, failures);
		return new FloatValidatorImpl(scope, configuration, name, value, context, failures);
	}
}