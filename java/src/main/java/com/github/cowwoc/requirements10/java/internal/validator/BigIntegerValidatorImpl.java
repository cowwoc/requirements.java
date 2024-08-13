/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements10.java.validator.BigIntegerValidator;

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
				NumberMessages.isNegative(this).toString());
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
				NumberMessages.isNotNegative(this).toString());
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
				NumberMessages.isZero(this).toString());
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
				NumberMessages.isNotZero(this).toString());
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
				NumberMessages.isPositive(this).toString());
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
				NumberMessages.isNotPositive(this).toString());
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
				NumberMessages.isMultipleOf(this, name, factor).toString());
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
				NumberMessages.isNotMultipleOf(this, name, factor).toString());
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