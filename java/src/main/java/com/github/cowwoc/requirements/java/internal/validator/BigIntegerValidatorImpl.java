/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements.java.validator.BigIntegerValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public final class BigIntegerValidatorImpl extends AbstractObjectValidator<BigIntegerValidator, BigInteger>
	implements BigIntegerValidator
{
	private final Comparables<BigIntegerValidator, BigInteger> comparables = new Comparables<>(this);

	/**
	 * @param scope         the application configuration
	 * @param configuration the validator configuration
	 * @param name          the name of the value
	 * @param value         the value
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public BigIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<BigInteger> value, Map<String, Object> context, List<ValidationFailure> failures)
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
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) < 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNegative(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotNegative()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) >= 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) == 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isZero(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) != 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isPositive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) > 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isPositive(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotPositive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigInteger.ZERO) <= 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public BigIntegerValidator isMultipleOf(BigInteger factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private BigIntegerValidator isMultipleOfImpl(BigInteger factor, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isMultipleOf(scope, this, name, factor).toString());
		}
		return this;
	}

	@Override
	public BigIntegerValidator isNotMultipleOf(BigInteger factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public BigIntegerValidator isNotMultipleOf(BigInteger factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private BigIntegerValidator isNotMultipleOfImpl(BigInteger factor, MaybeUndefined<String> name)
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !isMultipleOf(value, factor)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotMultipleOf(scope, this, name, factor).toString());
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
	public BigIntegerValidator isBetween(BigInteger minimum, boolean minimumInclusive, BigInteger maximum,
		boolean maximumInclusive)
	{
		return comparables.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}