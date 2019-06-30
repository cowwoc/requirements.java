/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;

import java.util.List;

/**
 * A PrimitiveNumberValidator that does nothing.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractPrimitiveNumberValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractObjectValidatorNoOp<S, T>
	implements ExtensiblePrimitiveNumberValidator<S, T>
{
	/**
	 * @param scope    the application configuration
	 * @param config   the instance configuration
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code scope}, {@code config} or {@code failures} are null
	 */
	public AbstractPrimitiveNumberValidatorNoOp(ApplicationScope scope, Configuration config,
	                                            List<ValidationFailure> failures)
	{
		super(scope, config, failures);
	}

	@Deprecated
	@Override
	public S isNull()
	{
		return getThis();
	}

	@Deprecated
	@Override
	public S isNotNull()
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
	public S isNotComparableTo(T value)
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

	@Override
	public S isNegative()
	{
		return getThis();
	}

	@Override
	public S isNotNegative()
	{
		return getThis();
	}

	@Override
	public S isZero()
	{
		return getThis();
	}

	@Override
	public S isNotZero()
	{
		return getThis();
	}

	@Override
	public S isPositive()
	{
		return getThis();
	}

	@Override
	public S isNotPositive()
	{
		return getThis();
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		return getThis();
	}
}
