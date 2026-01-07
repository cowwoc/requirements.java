/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.message.NumberMessages;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.BigIntegerValidator;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class BigIntegerValidatorImpl extends AbstractObjectValidator<BigIntegerValidator, BigInteger>
	implements BigIntegerValidator
{
	private final Comparables<BigIntegerValidator, BigInteger> comparables = new Comparables<>(this);

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
	public BigIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<BigInteger> value, Map<String, Optional<Object>> context,
		List<ValidationFailure> failures)
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
			(number.compareTo(BigInteger.ZERO) == 0 || number.remainder(factor).compareTo(BigInteger.ZERO) == 0);
	}

	@Override
	public BigIntegerValidator isNegative()
	{
		if (value.validationFailed(v -> v.compareTo(BigInteger.ZERO) < 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNegativeFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigIntegerValidator isNotNegative()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigInteger.ZERO) < 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotNegativeFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isZero()
	{
		if (value.validationFailed(v -> v.compareTo(BigInteger.ZERO) == 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isZeroFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigIntegerValidator isNotZero()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigInteger.ZERO) == 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotZeroFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isPositive()
	{
		if (value.validationFailed(v -> v.compareTo(BigInteger.ZERO) > 0))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isPositiveFailed(this).toString());
		}
		return this;
	}

	@Override
	@SuppressWarnings("PMD.LogicInversion")
	public BigIntegerValidator isNotPositive()
	{
		if (value.validationFailed(v -> !(v.compareTo(BigInteger.ZERO) > 0)))
		{
			failOnNull();
			addIllegalArgumentException(
				NumberMessages.isNotPositiveFailed(this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, null);
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, name);
	}

	private BigIntegerValidator isMultipleOfImpl(BigInteger factor, String name)
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
	public BigIntegerValidator isNotMultipleOf(BigInteger factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, null);
	}

	@Override
	public BigIntegerValidator isNotMultipleOf(BigInteger factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, name);
	}

	private BigIntegerValidator isNotMultipleOfImpl(BigInteger factor, String name)
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
	public BigIntegerValidator isLessThan(BigInteger maximumExclusive)
	{
		return comparables.isLessThan(maximumExclusive);
	}

	@Override
	public BigIntegerValidator isLessThan(BigInteger maximumExclusive, String name)
	{
		return comparables.isLessThan(maximumExclusive, name);
	}

	@Override
	public BigIntegerValidator isLessThanOrEqualTo(BigInteger maximumInclusive)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public BigIntegerValidator isLessThanOrEqualTo(BigInteger maximumInclusive, String name)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public BigIntegerValidator isGreaterThanOrEqualTo(BigInteger minimumInclusive)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public BigIntegerValidator isGreaterThanOrEqualTo(BigInteger minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public BigIntegerValidator isGreaterThan(BigInteger minimumExclusive)
	{
		return comparables.isGreaterThan(minimumExclusive);
	}

	@Override
	public BigIntegerValidator isGreaterThan(BigInteger minimumExclusive, String name)
	{
		return comparables.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public BigIntegerValidator isBetween(BigInteger minimumInclusive, BigInteger maximumExclusive)
	{
		return comparables.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public BigIntegerValidator isBetween(BigInteger minimum, boolean minimumIsInclusive, BigInteger maximum,
		boolean maximumIsInclusive)
	{
		return comparables.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}