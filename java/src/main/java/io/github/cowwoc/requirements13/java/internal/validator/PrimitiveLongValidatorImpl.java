/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.PrimitiveLongValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class PrimitiveLongValidatorImpl extends AbstractPrimitiveValidator<PrimitiveLongValidator, Long>
	implements PrimitiveLongValidator
{
	private final Longs<PrimitiveLongValidator> longs = new Longs<>(this);

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
	public PrimitiveLongValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Long> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public long getValue()
	{
		return value.orThrow(VALUE_IS_UNDEFINED);
	}

	@Override
	public long getValueOrDefault(long defaultValue)
	{
		return value.or(defaultValue);
	}

	@Override
	public PrimitiveLongValidator isEqualTo(long expected)
	{
		return longs.isEqualTo(expected);
	}

	@Override
	public PrimitiveLongValidator isEqualTo(long expected, String name)
	{
		return longs.isEqualTo(expected, name);
	}

	@Override
	public PrimitiveLongValidator isNotEqualTo(long unwanted)
	{
		return longs.isNotEqualTo(unwanted);
	}

	@Override
	public PrimitiveLongValidator isNotEqualTo(long unwanted, String name)
	{
		return longs.isNotEqualTo(unwanted, name);
	}

	@Override
	public PrimitiveLongValidator isNegative()
	{
		return longs.isNegative();
	}

	@Override
	public PrimitiveLongValidator isNotNegative()
	{
		return longs.isNotNegative();
	}

	@Override
	public PrimitiveLongValidator isZero()
	{
		return longs.isZero();
	}

	@Override
	public PrimitiveLongValidator isNotZero()
	{
		return longs.isNotZero();
	}

	@Override
	public PrimitiveLongValidator isPositive()
	{
		return longs.isPositive();
	}

	@Override
	public PrimitiveLongValidator isNotPositive()
	{
		return longs.isNotPositive();
	}

	@Override
	public PrimitiveLongValidator isLessThan(long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThan(long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isBetween(long minimumInclusive, long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isBetween(long minimum, boolean minimumIsInclusive, long maximum,
		boolean maximumIsInclusive)
	{
		return longs.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public PrimitiveLongValidator isMultipleOf(long factor)
	{
		return longs.isMultipleOf(factor);
	}

	@Override
	public PrimitiveLongValidator isMultipleOf(long factor, String name)
	{
		return longs.isMultipleOf(factor, name);
	}

	@Override
	public PrimitiveLongValidator isNotMultipleOf(long factor)
	{
		return longs.isNotMultipleOf(factor);
	}

	@Override
	public PrimitiveLongValidator isNotMultipleOf(long factor, String name)
	{
		return longs.isNotMultipleOf(factor, name);
	}

	@Override
	public PrimitiveLongValidator isLessThan(Long maximumExclusive)
	{
		return longs.isLessThan(maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThan(Long maximumExclusive, String name)
	{
		return longs.isLessThan(maximumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(Long maximumInclusive)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public PrimitiveLongValidator isLessThanOrEqualTo(Long maximumInclusive, String name)
	{
		return longs.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(Long minimumInclusive)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThanOrEqualTo(Long minimumInclusive, String name)
	{
		return longs.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(Long minimumExclusive)
	{
		return longs.isGreaterThan(minimumExclusive);
	}

	@Override
	public PrimitiveLongValidator isGreaterThan(Long minimumExclusive, String name)
	{
		return longs.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public PrimitiveLongValidator isBetween(Long minimumInclusive, Long maximumExclusive)
	{
		return longs.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public PrimitiveLongValidator isBetween(Long minimum, boolean minimumIsInclusive, Long maximum,
		boolean maximumIsInclusive)
	{
		return longs.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}