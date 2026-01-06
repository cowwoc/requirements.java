/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.ShortValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class ShortValidatorImpl extends AbstractObjectValidator<ShortValidator, Short>
	implements ShortValidator
{
	private final Shorts<ShortValidator> shorts = new Shorts<>(this);

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
	public ShortValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<Short> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public ShortValidator isNegative()
	{
		return shorts.isNegative();
	}

	@Override
	public ShortValidator isNotNegative()
	{
		return shorts.isNotNegative();
	}

	@Override
	public ShortValidator isZero()
	{
		return shorts.isZero();
	}

	@Override
	public ShortValidator isNotZero()
	{
		return shorts.isNotZero();
	}

	@Override
	public ShortValidator isPositive()
	{
		return shorts.isPositive();
	}

	@Override
	public ShortValidator isNotPositive()
	{
		return shorts.isNotPositive();
	}

	@Override
	public ShortValidator isLessThan(short maximumExclusive)
	{
		return shorts.isLessThan(maximumExclusive);
	}

	@Override
	public ShortValidator isLessThan(short maximumExclusive, String name)
	{
		return shorts.isLessThan(maximumExclusive, name);
	}

	@Override
	public ShortValidator isLessThanOrEqualTo(short maximumInclusive)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public ShortValidator isLessThanOrEqualTo(short maximumInclusive, String name)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public ShortValidator isGreaterThanOrEqualTo(short minimumInclusive)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public ShortValidator isGreaterThanOrEqualTo(short minimumInclusive, String name)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public ShortValidator isGreaterThan(short minimumExclusive)
	{
		return shorts.isGreaterThan(minimumExclusive);
	}

	@Override
	public ShortValidator isGreaterThan(short minimumExclusive, String name)
	{
		return shorts.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public ShortValidator isBetween(short minimumInclusive, short maximumExclusive)
	{
		return shorts.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public ShortValidator isBetween(short minimum, boolean minimumIsInclusive, short maximum,
		boolean maximumIsInclusive)
	{
		return shorts.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}

	@Override
	public ShortValidator isMultipleOf(short factor)
	{
		return shorts.isMultipleOf(factor);
	}

	@Override
	public ShortValidator isMultipleOf(short factor, String name)
	{
		return shorts.isMultipleOf(factor, name);
	}

	@Override
	public ShortValidator isNotMultipleOf(short factor)
	{
		return shorts.isNotMultipleOf(factor);
	}

	@Override
	public ShortValidator isNotMultipleOf(short factor, String name)
	{
		return shorts.isNotMultipleOf(factor, name);
	}

	@Override
	public ShortValidator isLessThan(Short maximumExclusive)
	{
		return shorts.isLessThan(maximumExclusive);
	}

	@Override
	public ShortValidator isLessThan(Short maximumExclusive, String name)
	{
		return shorts.isLessThan(maximumExclusive, name);
	}

	@Override
	public ShortValidator isLessThanOrEqualTo(Short maximumInclusive)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public ShortValidator isLessThanOrEqualTo(Short maximumInclusive, String name)
	{
		return shorts.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public ShortValidator isGreaterThanOrEqualTo(Short minimumInclusive)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public ShortValidator isGreaterThanOrEqualTo(Short minimumInclusive, String name)
	{
		return shorts.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public ShortValidator isGreaterThan(Short minimumExclusive)
	{
		return shorts.isGreaterThan(minimumExclusive);
	}

	@Override
	public ShortValidator isGreaterThan(Short minimumExclusive, String name)
	{
		return shorts.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public ShortValidator isBetween(Short minimumInclusive, Short maximumExclusive)
	{
		return shorts.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public ShortValidator isBetween(Short minimum, boolean minimumIsInclusive, Short maximum,
		boolean maximumIsInclusive)
	{
		return shorts.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}