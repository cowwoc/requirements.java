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
import com.github.cowwoc.requirements.java.type.BigIntegerValidator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BigIntegerValidatorImpl extends AbstractObjectValidator<BigIntegerValidator, BigInteger>
	implements BigIntegerValidator
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
	public BigIntegerValidatorImpl(ApplicationScope scope, AbstractValidator<?> validator, String name,
		BigInteger value)
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
	public BigIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		BigInteger value)
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
	private BigIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		BigInteger value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(BigInteger number, BigInteger factor)
	{
		return factor.compareTo(BigInteger.ZERO) != 0 &&
		       (number.compareTo(BigInteger.ZERO) == 0 ||
		        number.remainder(factor).compareTo(BigInteger.ZERO) == 0);
	}

	@Override
	public BigIntegerValidator isNegative()
	{
		if (hasFailed())
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, null).toString());
		else if (value == null)
		{
			addNullPointerException(
				ObjectMessages.isNotNull(scope, this, this.name).toString());
		}
		else if (value.compareTo(BigInteger.ZERO) >= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotNegative()
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
		else if (value.compareTo(BigInteger.ZERO) < 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isZero()
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
		else if (value.compareTo(BigInteger.ZERO) != 0)
		{
			addIllegalArgumentException(
				NumberMessages.isZero(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotZero()
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
		else if (value.compareTo(BigInteger.ZERO) == 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this, this.name).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isPositive()
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
		else if (value.compareTo(BigInteger.ZERO) <= 0)
		{
			addIllegalArgumentException(
				NumberMessages.isPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotPositive()
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
		else if (value.compareTo(BigInteger.ZERO) > 0)
		{
			addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this, this.name, value).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor)
	{
		scope.getInternalValidator().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		internalValidator.requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, name);
	}

	private BigIntegerValidator isMultipleOfImpl(BigInteger factor, String name)
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
	public BigIntegerValidator isNotMultipleOf(BigInteger factor)
	{
		scope.getInternalValidator().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public BigIntegerValidator isNotMultipleOf(BigInteger factor, String name)
	{
		JavaValidatorsImpl internalValidator = scope.getInternalValidator();
		internalValidator.requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		internalValidator.requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, name);
	}

	private BigIntegerValidator isNotMultipleOfImpl(BigInteger factor, String name)
	{
		if (hasFailed())
		{
			addIllegalArgumentException(NumberMessages.isNotMultipleOf(scope, this,
				this.name, null, name, factor).toString());
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
	public BigIntegerValidator isLessThan(BigInteger maximumExclusive)
	{
		return isLessThanImpl(maximumExclusive, null);
	}

	@Override
	public BigIntegerValidator isLessThan(BigInteger maximumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");

		return isLessThanImpl(maximumExclusive, name);
	}

	private BigIntegerValidator isLessThanImpl(BigInteger maximumExclusive, String name)
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
			addIllegalArgumentException(ComparableMessages.isLessThan(scope, this, this.name,
				value, name, maximumExclusive).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isLessThanOrEqualTo(BigInteger maximumInclusive)
	{
		return isLessThanOrEqualToImpl(maximumInclusive, null);
	}

	@Override
	public BigIntegerValidator isLessThanOrEqualTo(BigInteger maximumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		return isLessThanOrEqualToImpl(maximumInclusive, name);
	}

	private BigIntegerValidator isLessThanOrEqualToImpl(BigInteger maximumInclusive, String name)
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
	public BigIntegerValidator isGreaterThanOrEqualTo(BigInteger minimumInclusive)
	{
		return isGreaterThanOrEqualToImpl(minimumInclusive, null);
	}

	@Override
	public BigIntegerValidator isGreaterThanOrEqualTo(BigInteger minimumInclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		return isGreaterThanOrEqualToImpl(minimumInclusive, name);
	}

	private BigIntegerValidator isGreaterThanOrEqualToImpl(BigInteger minimumInclusive, String name)
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
	public BigIntegerValidator isGreaterThan(BigInteger minimumExclusive)
	{
		return isGreaterThanImpl(minimumExclusive, null);
	}

	@Override
	public BigIntegerValidator isGreaterThan(BigInteger minimumExclusive, String name)
	{
		scope.getInternalValidator().requireThat(name, "name").isStripped().
			isNotEqualTo(this.name, "Actual");
		return isGreaterThanImpl(minimumExclusive, name);
	}

	private BigIntegerValidator isGreaterThanImpl(BigInteger minimumExclusive, String name)
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
	public BigIntegerValidator isBetween(BigInteger minimumInclusive, BigInteger maximumExclusive)
	{
		return isBetween(minimumInclusive, true, maximumExclusive, false);
	}

	@Override
	public BigIntegerValidator isBetween(BigInteger minimum, boolean minimumInclusive, BigInteger maximum,
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

	private boolean minimumFailed(BigInteger minimum, boolean minimumInclusive)
	{
		if (minimumInclusive)
			return value.compareTo(minimum) < 0;
		return value.compareTo(minimum) <= 0;
	}

	private boolean maximumFailed(BigInteger maximum, boolean maximumInclusive)
	{
		if (maximumInclusive)
			return value.compareTo(maximum) > 0;
		return value.compareTo(maximum) >= 0;
	}
}