/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.internal.validator;

import io.github.cowwoc.requirements12.java.ValidationFailure;
import io.github.cowwoc.requirements12.java.internal.Configuration;
import io.github.cowwoc.requirements12.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements12.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements12.java.validator.PrimitiveIntegerValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PrimitiveIntegerValidatorImpl
	extends AbstractPrimitiveValidator<PrimitiveIntegerValidator, Integer>
	implements PrimitiveIntegerValidator
{
	private final Integers<PrimitiveIntegerValidator> integers = new Integers<>(this);

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
	public PrimitiveIntegerValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Integer> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public int getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public int getValueOrDefault(int defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveIntegerValidator isEqualTo(int expected)
	{
		return integers.isEqualTo(expected);
	}

	@Override
	public PrimitiveIntegerValidator isEqualTo(int expected, String name)
	{
		return integers.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveIntegerValidator isNotEqualTo(int unwanted)
	{
		return integers.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveIntegerValidator isNotEqualTo(int unwanted, String name)
	{
		return integers.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveIntegerValidator isNegative()
	{
		return integers.isNegative();
	}

	@Override
	public PrimitiveIntegerValidator isNotNegative()
	{
		return integers.isNotNegative();
	}

	@Override
	public PrimitiveIntegerValidator isZero()
	{
		return integers.isZero();
	}

	@Override
	public PrimitiveIntegerValidator isNotZero()
	{
		return integers.isNotZero();
	}

	@Override
	public PrimitiveIntegerValidator isPositive()
	{
		return integers.isPositive();
	}

	@Override
	public PrimitiveIntegerValidator isNotPositive()
	{
		return integers.isNotPositive();
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(int maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(int maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(int maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(int maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(int minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(int minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(int minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(int minimumInclusive, int maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(int minimum, boolean minimumIsInclusive, int maximum,
		boolean maximumIsInclusive)
	{
		return integers.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isMultipleOf(int factor)
	{
		return integers.isMultipleOf(factor);
	}

	@Override
	public PrimitiveIntegerValidator isMultipleOf(int factor, String name)
	{
		return integers.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveIntegerValidator isNotMultipleOf(int factor)
	{
		return integers.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveIntegerValidator isNotMultipleOf(int factor, String name)
	{
		return integers.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(Integer maximumExclusive)
	{
		return integers.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThan(Integer maximumExclusive, String name)
	{
		return integers.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isLessThanOrEqualTo(Integer maximumInclusive, String name)
	{
		return integers.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThanOrEqualTo(Integer minimumInclusive, String name)
	{
		return integers.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(Integer minimumExclusive)
	{
		return integers.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isGreaterThan(Integer minimumExclusive, String name)
	{
		return integers.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(Integer minimumInclusive, Integer maximumExclusive)
	{
		return integers.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveIntegerValidator isBetween(Integer minimum, boolean minimumIsInclusive, Integer maximum,
		boolean maximumIsInclusive)
	{
		return integers.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}