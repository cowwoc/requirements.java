/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.validator.FloatValidator;
import com.github.cowwoc.requirements10.java.internal.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

public final class FloatValidatorImpl extends AbstractObjectValidator<FloatValidator, Float>
	implements FloatValidator
{
	private final Floats<FloatValidator> floats = new Floats<>(this);

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
	public FloatValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<Float> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public FloatValidator isNegative()
	{
		return floats.isNegative();
	}

	@Override
	public FloatValidator isNotNegative()
	{
		return floats.isNotNegative();
	}

	@Override
	public FloatValidator isZero()
	{
		return floats.isZero();
	}

	@Override
	public FloatValidator isNotZero()
	{
		return floats.isNotZero();
	}

	@Override
	public FloatValidator isPositive()
	{
		return floats.isPositive();
	}

	@Override
	public FloatValidator isNotPositive()
	{
		return floats.isNotPositive();
	}

	@Override
	public FloatValidator isLessThan(float maximumExclusive)
	{
		return floats.isLessThan(maximumExclusive);
	}

	@Override
	public FloatValidator isLessThan(float maximumExclusive, String name)
	{
		return floats.isLessThan(maximumExclusive, name);
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(float maximumInclusive)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(float maximumInclusive, String name)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public FloatValidator isGreaterThanOrEqualTo(float minimumInclusive)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public FloatValidator isGreaterThanOrEqualTo(float minimumInclusive, String name)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public FloatValidator isGreaterThan(float minimumExclusive)
	{
		return floats.isGreaterThan(minimumExclusive);
	}

	@Override
	public FloatValidator isGreaterThan(float minimumExclusive, String name)
	{
		return floats.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public FloatValidator isBetween(float minimumInclusive, float maximumExclusive)
	{
		return floats.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public FloatValidator isBetween(float minimum, boolean minimumInclusive, float maximum,
		boolean maximumInclusive)
	{
		return floats.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public FloatValidator isMultipleOf(float factor)
	{
		return floats.isMultipleOf(factor);
	}

	@Override
	public FloatValidator isMultipleOf(float factor, String name)
	{
		return floats.isMultipleOf(factor, name);
	}

	@Override
	public FloatValidator isNotMultipleOf(float factor)
	{
		return floats.isNotMultipleOf(factor);
	}

	@Override
	public FloatValidator isNotMultipleOf(float factor, String name)
	{
		return floats.isNotMultipleOf(factor, name);
	}

	@Override
	public FloatValidator isLessThan(Float maximumExclusive)
	{
		return floats.isLessThan(maximumExclusive);
	}

	@Override
	public FloatValidator isLessThan(Float maximumExclusive, String name)
	{
		return floats.isLessThan(maximumExclusive, name);
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(Float maximumInclusive)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public FloatValidator isLessThanOrEqualTo(Float maximumInclusive, String name)
	{
		return floats.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public FloatValidator isGreaterThanOrEqualTo(Float minimumInclusive)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public FloatValidator isGreaterThanOrEqualTo(Float minimumInclusive, String name)
	{
		return floats.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public FloatValidator isGreaterThan(Float minimumExclusive)
	{
		return floats.isGreaterThan(minimumExclusive);
	}

	@Override
	public FloatValidator isGreaterThan(Float minimumExclusive, String name)
	{
		return floats.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public FloatValidator isBetween(Float minimumInclusive, Float maximumExclusive)
	{
		return floats.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public FloatValidator isBetween(Float minimum, boolean minimumInclusive, Float maximum,
		boolean maximumInclusive)
	{
		return floats.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}

	@Override
	public FloatValidator isNumber()
	{
		return floats.isNumber();
	}

	@Override
	public FloatValidator isNotNumber()
	{
		return floats.isNotNumber();
	}

	@Override
	public FloatValidator isFinite()
	{
		return floats.isFinite();
	}

	@Override
	public FloatValidator isInfinite()
	{
		return floats.isInfinite();
	}

	@Override
	public FloatValidator isWholeNumber()
	{
		return floats.isWholeNumber();
	}

	@Override
	public FloatValidator isNotWholeNumber()
	{
		return floats.isNotWholeNumber();
	}
}