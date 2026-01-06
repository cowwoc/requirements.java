/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.validator;

import io.github.cowwoc.requirements13.java.ValidationFailure;
import io.github.cowwoc.requirements13.java.internal.Configuration;
import io.github.cowwoc.requirements13.java.internal.scope.ApplicationScope;
import io.github.cowwoc.requirements13.java.internal.util.ValidationTarget;
import io.github.cowwoc.requirements13.java.validator.ComparableValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	 * @param value         the value being validated
	 * @param context       the contextual information set by a parent validator or the user
	 * @param failures      the list of validation failures
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains whitespace, or is empty
	 * @throws AssertionError           if {@code scope}, {@code configuration}, {@code value}, {@code context}
	 *                                  or {@code failures} are null
	 */
	public ComparableValidatorImpl(ApplicationScope scope, Configuration configuration, String name,
		ValidationTarget<T> value, Map<String, Optional<Object>> context, List<ValidationFailure> failures)
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
	public ComparableValidator<T> isBetween(T minimum, boolean minimumIsInclusive, T maximum,
		boolean maximumIsInclusive)
	{
		return comparables.isBetween(minimum, minimumIsInclusive, maximum, maximumIsInclusive);
	}
}