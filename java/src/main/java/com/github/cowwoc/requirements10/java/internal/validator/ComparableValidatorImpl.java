/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.internal.validator;

import com.github.cowwoc.requirements10.java.internal.scope.ApplicationScope;
import com.github.cowwoc.requirements10.java.validator.ComparableValidator;
import com.github.cowwoc.requirements10.java.Configuration;
import com.github.cowwoc.requirements10.java.ValidationFailure;
import com.github.cowwoc.requirements10.java.internal.util.MaybeUndefined;

import java.util.List;
import java.util.Map;

/**
 * @param <T> the type of the value that is being validated
 */
public final class ComparableValidatorImpl<T extends Comparable<T>>
	extends AbstractObjectValidator<ComparableValidator<T>, T>
	implements ComparableValidator<T>
{
	private final Comparables<ComparableValidator<T>, T> comparables = new Comparables<>(this);

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
	public ComparableValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		MaybeUndefined<T> value, Map<String, Object> context, List<ValidationFailure> failures)
	{
		super(scope, configuration, name, value, context, failures);
	}

	@Override
	public ComparableValidator<T> isLessThan(T maximumExclusive)
	{
		return comparables.isLessThan(maximumExclusive);
	}

	@Override
	public ComparableValidator<T> isLessThan(T maximumExclusive, String name)
	{
		return comparables.isLessThan(maximumExclusive, name);
	}

	@Override
	public ComparableValidator<T> isLessThanOrEqualTo(T maximumInclusive)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive);
	}

	@Override
	public ComparableValidator<T> isLessThanOrEqualTo(T maximumInclusive, String name)
	{
		return comparables.isLessThanOrEqualTo(maximumInclusive, name);
	}

	@Override
	public ComparableValidator<T> isGreaterThanOrEqualTo(T minimumInclusive)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive);
	}

	@Override
	public ComparableValidator<T> isGreaterThanOrEqualTo(T minimumInclusive, String name)
	{
		return comparables.isGreaterThanOrEqualTo(minimumInclusive, name);
	}

	@Override
	public ComparableValidator<T> isGreaterThan(T minimumExclusive)
	{
		return comparables.isGreaterThan(minimumExclusive);
	}

	@Override
	public ComparableValidator<T> isGreaterThan(T minimumExclusive, String name)
	{
		return comparables.isGreaterThan(minimumExclusive, name);
	}

	@Override
	public ComparableValidator<T> isBetween(T minimumInclusive, T maximumExclusive)
	{
		return comparables.isBetween(minimumInclusive, maximumExclusive);
	}

	@Override
	public ComparableValidator<T> isBetween(T minimum, boolean minimumInclusive, T maximum,
		boolean maximumInclusive)
	{
		return comparables.isBetween(minimum, minimumInclusive, maximum, maximumInclusive);
	}
}