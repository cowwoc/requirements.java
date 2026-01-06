/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.message.NumberMessages;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.BigDecimalValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveIntegerValidator;
import io.github.cowwoc.requirements13.java.validator.PrimitiveUnsignedIntegerValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class BigDecimalValidatorImpl extends AbstractObjectValidator<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
{
	private final Comparables<BigDecimalValidator, BigDecimal> comparables = new Comparables<>(this);

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public BigDecimalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<BigDecimal> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
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
			(number.compareTo(BigDecimal.ZERO) == 0 || number.remainder(factor).compareTo(BigDecimal.ZERO) == 0);
	}

	@Override
	public BigDecimalValidator isNegative()
	{
		if (value.validationFailed(v -> v.compareTo(BigDecimal.ZERO) < 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNegativeFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigDecimalValidator isNotNegative()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigDecimal.ZERO) < 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotNegativeFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isZero()
	{
		if (value.validationFailed(v -> v.compareTo(BigDecimal.ZERO) == 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isZeroFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigDecimalValidator isNotZero()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigDecimal.ZERO) == 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotZeroFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isPositive()
	{
		if (value.validationFailed(v -> v.compareTo(BigDecimal.ZERO) > 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isPositiveFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigDecimalValidator isNotPositive()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigDecimal.ZERO) > 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotPositiveFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isWholeNumber()
	{
		if (value.validationFailed(BigDecimalValidatorImpl::isWholeNumber))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isWholeNumberFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotWholeNumber()
	{
		if (value.validationFailed(v -> !isWholeNumber(v)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotWholeNumberFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, name);
	}

	private BigDecimalValidator isMultipleOfImpl(BigDecimal factor, String name)
	{
		if (value.validationFailed(v -> isMultipleOf(v, factor)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isMultipleOfFailed(this, name, factor).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, name);
	}

	private BigDecimalValidator isNotMultipleOfImpl(BigDecimal factor, String name)
	{
		if (value.validationFailed(v -> !isMultipleOf(v, factor)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotMultipleOfFailed(this, name, factor).toString());
		}
		return this;
	}

	@Override
	public PrimitiveUnsignedIntegerValidator precision()
	{
		failOnNull();
		ValidationTarget<BigDecimal> nullToInvalid = value.nullToInvalid();
		PrimitiveUnsignedIntegerValidatorImpl newValidator = new PrimitiveUnsignedIntegerValidatorImpl(scope,
			configuration, name + ".precision()", nullToInvalid.map(BigDecimal::precision), context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public PrimitiveIntegerValidator scale()
	{
		failOnNull();
		ValidationTarget<BigDecimal> nullToInvalid = value.nullToInvalid();
		PrimitiveIntegerValidatorImpl newValidator = new PrimitiveIntegerValidatorImpl(scope, configuration,
			name + ".scale()", nullToInvalid.map(BigDecimal::scale), context, failures);
		nullToInvalid.ifValid(v -> newValidator.withContext(v, name));
		return newValidator;
	}

	@Override
	public BigDecimalValidator isLessThan(BigDecimal maximumExclusive)
	{
		return comparables.isLessThan(maximumExclusive);
	}

	@Override
	public BigDecimalValidator isLessThan(BigDecimal maximumExclusive, String name)
	{
		return comparables.isLessThan(maximumExclusive, name);
	}

	@Override
	public BigDecimalValidator isLessThanOrEqualTo(BigDecimal maximumInclusive)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public BigDecimalValidator isLessThanOrEqualTo(BigDecimal maximumInclusive, String name)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public BigDecimalValidator isGreaterThanOrEqualTo(BigDecimal minimumInclusive)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public BigDecimalValidator isGreaterThanOrEqualTo(BigDecimal minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public BigDecimalValidator isGreaterThan(BigDecimal minimumExclusive)
	{
		return comparables.isGreaterThan(minimumExclusive);
	}

	@Override
	public BigDecimalValidator isGreaterThan(BigDecimal minimumExclusive, String name)
	{
		return comparables.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public BigDecimalValidator isBetween(BigDecimal minimumInclusive, BigDecimal maximumExclusive)
	{
		return comparables.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public BigDecimalValidator isBetween(BigDecimal minimum, boolean minimumIsInclusive, BigDecimal maximum,
		boolean maximumIsInclusive)
	{
		return comparables.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}