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
import com.github.cowwoc.requirements.java.type.BigDecimalValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class BigDecimalValidatorImpl extends AbstractObjectValidator<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
{
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
	public BigDecimalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		BigDecimal value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(BigDecimal value)
	{
		// Based on https://stackoverflow.com/a/12748321/14731
		return value.signum() == 0 || value.stripTrailingZeros().scale() <= 0;
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(BigDecimal number, BigDecimal factor)
	{
		return factor.compareTo(BigDecimal.ZERO) != 0 &&
		       (number.compareTo(BigDecimal.ZERO) == 0 ||
		        number.remainder(factor).compareTo(BigDecimal.ZERO) == 0);
	}

	@Override
	public BigDecimalValidator isNegative()
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
		else if (value.compareTo(BigDecimal.ZERO) >= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotNegative()
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
		else if (value.compareTo(BigDecimal.ZERO) < 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isZero()
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
		else if (value.compareTo(BigDecimal.ZERO) != 0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotZero()
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
		else if (value.compareTo(BigDecimal.ZERO) == 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isPositive()
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
		else if (value.compareTo(BigDecimal.ZERO) <= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotPositive()
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
		else if (value.compareTo(BigDecimal.ZERO) > 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isWholeNumber()
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
	public BigDecimalValidator isNotWholeNumber()
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
	public BigDecimalValidator isMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidator().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal factor, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");

		internalValidator.requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, name);
	}

	private BigDecimalValidator isMultipleOfImpl(BigDecimal factor, String name)
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
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidator().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		internalValidator.requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, name);
	}

	private BigDecimalValidator isNotMultipleOfImpl(BigDecimal factor, String name)
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
	public BigDecimalValidator isLessThan(BigDecimal maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public BigDecimalValidator isLessThan(BigDecimal maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isLessThanImpl(maximumExclusive, name);
	}

	private BigDecimalValidator isLessThanImpl(BigDecimal maximumExclusive, String name)
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
			addIllegalArgumentException(
				ComparableMessages.isLessThan(scope, this, this.name, value, name, maximumExclusive).
					toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isLessThanOrEqualTo(BigDecimal maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public BigDecimalValidator isLessThanOrEqualTo(BigDecimal maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private BigDecimalValidator isLessThanOrEqualToImpl(BigDecimal maximumInclusive, String name)
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
	public BigDecimalValidator isGreaterThanOrEqualTo(BigDecimal minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public BigDecimalValidator isGreaterThanOrEqualTo(BigDecimal minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private BigDecimalValidator isGreaterThanOrEqualToImpl(BigDecimal minimumInclusive, String name)
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
	public BigDecimalValidator isGreaterThan(BigDecimal minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public BigDecimalValidator isGreaterThan(BigDecimal minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "the same name as the value");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private BigDecimalValidator isGreaterThanImpl(BigDecimal minimumExclusive, String name)
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
				ComparableMessages.isGreaterThan(scope, this, this.name, value, name, minimumExclusive).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isBetween(BigDecimal minimumInclusive, BigDecimal maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public BigDecimalValidator isBetween(BigDecimal minimum, boolean minimumInclusive, BigDecimal maximum,
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
				ComparableMessages.isBetween(scope, this, this.name, value, minimum,
					minimumInclusive, maximum, maximumInclusive, null).toString());
		}
		return this;
	}

	private boolean minimumFailed(BigDecimal minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value.compareTo(minimum) < 0;
		return value.compareTo(minimum) <= 0;
	}

	private boolean maximumFailed(BigDecimal maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value.compareTo(maximum) > 0;
		return value.compareTo(maximum) >= 0;
	}

	@Override
	public PrimitiveUnsignedIntegerValidatorImpl precision()
	{
		if (hasFailed())
		{
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".precision()",
				0, name, value, null, context, failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".precision()",
				0, name, value, null, context, failures);
		}
		return new PrimitiveUnsignedIntegerValidatorImpl(scope, configuration, name + ".precision()",
			value.precision(), name, value, null, context, failures);
	}

	@Override
	public PrimitiveIntegerValidatorImpl scale()
	{
		if (hasFailed())
		{
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name + ".scale()", 0, context,
				failures);
		}
		if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
			return new PrimitiveIntegerValidatorImpl(scope, configuration, name + ".scale()", 0, context,
				failures);
		}
		return new PrimitiveIntegerValidatorImpl(scope, configuration, name + ".scale()", value.scale(),
			context, failures);
	}
}