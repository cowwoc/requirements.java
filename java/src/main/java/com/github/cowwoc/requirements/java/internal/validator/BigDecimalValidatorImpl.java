/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.validator;

import com.github.cowwoc.requirements.java.validator.BigDecimalValidator;
import com.github.cowwoc.requirements.java.Configuration;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.message.NumberMessages;
import com.github.cowwoc.requirements.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements.java.internal.util.MaybeUndefined;
import com.github.cowwoc.requirements.java.validator.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.validator.PrimitiveUnsignedIntegerValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public final class BigDecimalValidatorImpl extends AbstractObjectValidator<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
{
	private final Comparables<BigDecimalValidator, BigDecimal> comparables = new Comparables<>(this);

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
	public BigDecimalValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<BigDecimal> value, Map<String, Object> context, List<ValidationFailure> failures)
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
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) < 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNegative(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotNegative()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) >= 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotNegative(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) == 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isZero(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotZero()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) != 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotZero(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isPositive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) > 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isPositive(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotPositive()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> value.compareTo(BigDecimal.ZERO) <= 0))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotPositive(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isWholeNumber()
	{
		if (value.isNull())
			onNull();
		switch (value.test(BigDecimalValidatorImpl::isWholeNumber))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isWholeNumber(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isNotWholeNumber()
	{
		if (value.isNull())
			onNull();
		switch (value.test(value -> !isWholeNumber(value)))
		{
			case UNDEFINED, FALSE -> addIllegalArgumentException(
				NumberMessages.isNotWholeNumber(scope, this).toString());
		}
		return this;
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public BigDecimalValidator isMultipleOf(BigDecimal factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private BigDecimalValidator isMultipleOfImpl(BigDecimal factor, MaybeUndefined<String> name)
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
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor)
	{
		scope.getInternalValidators().requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, MaybeUndefined.undefined());
	}

	@Override
	public BigDecimalValidator isNotMultipleOf(BigDecimal factor, String name)
	{
		requireThatNameIsUnique(name).
			requireThat(factor, "factor").isNotNull();
		return isNotMultipleOfImpl(factor, MaybeUndefined.defined(name));
	}

	private BigDecimalValidator isNotMultipleOfImpl(BigDecimal factor, MaybeUndefined<String> name)
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
	public PrimitiveUnsignedIntegerValidator precision()
	{
		if (value.isNull())
			onNull();
		PrimitiveUnsignedIntegerValidatorImpl newValidator = new PrimitiveUnsignedIntegerValidatorImpl(scope,
			configuration, name + ".precision()",
			value.nullToUndefined().mapDefined(BigDecimal::precision), context, failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
		return newValidator;
	}

	@Override
	public PrimitiveIntegerValidator scale()
	{
		if (value.isNull())
			onNull();
		PrimitiveIntegerValidatorImpl newValidator = new PrimitiveIntegerValidatorImpl(scope, configuration,
			name + ".scale()", value.nullToUndefined().mapDefined(BigDecimal::scale), context, failures);
		value.ifDefined(value -> newValidator.withContext(value, name));
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
	public BigDecimalValidator isBetween(BigDecimal minimum, boolean minimumInclusive, BigDecimal maximum,
		boolean maximumInclusive)
	{
		return comparables.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}