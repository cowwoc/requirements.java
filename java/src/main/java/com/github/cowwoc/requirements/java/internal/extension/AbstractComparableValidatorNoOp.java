/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleComparableValidator;

import java.util.List;

/**
 * A {@code ExtensibleComparableValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractComparableValidatorNoOp<S, T extends Comparable<? super T>>
	extends AbstractObjectValidatorNoOp<S, T>
	implements ExtensibleComparableValidator<S, T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	protected AbstractComparableValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	public S isGreaterThan(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(T expected)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(T expected, String name)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other, String name)
	{
		return getThis();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		return getThis();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		return getThis();
	}
}
