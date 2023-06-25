/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.implementation.message.ComparableMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.implementation.message.ObjectMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.type.FloatValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class FloatValidatorImpl extends AbstractObjectValidator<FloatValidator, Float>
	implements FloatValidator
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
	public FloatValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		Float value)
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
	public FloatValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Float value)
	{
		this(scope, configuration, name, value, new HashMap<>(), new ArrayList<>());
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
	private FloatValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		Float value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(float value)
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
		return factor != 0.0 && (number == 0.0 || (number % factor == 0));
	}

	@Override
	public FloatValidator isNegative()
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
		else if (Float.isNaN(value) || value >= 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNotNegative()
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
		else if (!Float.isNaN(value) && value < 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isZero()
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
		else if (Float.isNaN(value) || value != 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNotZero()
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
		else if (!Float.isNaN(value) && value == 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isPositive()
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
		else if (Float.isNaN(value) || value <= 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNotPositive()
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
		else if (!Float.isNaN(value) && value > 0.0f)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (Float.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNotNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Float.isNaN(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isFinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Float.isFinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isFinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isInfinite()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!Float.isInfinite(value))
		{
			addIllegalArgumentException(
				NumberMessages.isInfinite(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (!isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isNotWholeNumber()
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, null).toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (isWholeNumber(value))
		{
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isMultipleOf(float factor)
	{
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public FloatValidator isMultipleOf(float factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isMultipleOfImpl(factor, name);
	}

	private FloatValidator isMultipleOfImpl(float factor, String name)
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
	public FloatValidator isNotMultipleOf(float factor)
	{
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public FloatValidator isNotMultipleOf(float factor, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isNotMultipleOfImpl(factor, name);
	}

	private FloatValidator isNotMultipleOfImpl(float factor, String name)
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
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, this.name, value, name, factor).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isLessThan(float maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public FloatValidator isLessThan(float maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isLessThanImpl(maximumExclusive, name);
	}

	private FloatValidator isLessThanImpl(float maximumExclusive, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, null, name, maximumExclusive).
					toString());
		}
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(maximumExclusive) >= 0)
		{
			addIllegalArgumentException(ComparableMessages.isLessThan(scope, this, this.name,
				value, name, maximumExclusive).toString());
		}
		return this;
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(float maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(float maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private FloatValidator isLessThanOrEqualToImpl(float maximumInclusive, String name)
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
	public FloatValidator isGreaterThanOrEqualTo(float minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public FloatValidator isGreaterThanOrEqualTo(float minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private FloatValidator isGreaterThanOrEqualToImpl(float minimumInclusive, String name)
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
	public FloatValidator isGreaterThan(float minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public FloatValidator isGreaterThan(float minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isGreaterThanImpl(minimumExclusive, name);
	}

	private FloatValidator isGreaterThanImpl(float minimumExclusive, String name)
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
	public FloatValidator isBetween(float minimumInclusive, float maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public FloatValidator isBetween(float minimum, boolean minimumInclusive, float maximum,
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

	private boolean minimumFailed(float minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value.compareTo(minimum) < 0;
		return value.compareTo(minimum) <= 0;
	}

	private boolean maximumFailed(float maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value.compareTo(maximum) > 0;
		return value.compareTo(maximum) >= 0;
	}
}