/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

import java.util.List;

/**
 * An {@code ExtensiblePrimitiveNumberValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractPrimitiveNumberValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractObjectValidatorNoOp<S, T>
	implements ExtensiblePrimitiveValidator<S, T>, ExtensibleNumberValidator<S, T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public AbstractPrimitiveNumberValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	@Deprecated
	public S isNull()
	{
		return getThis();
	}

	@Override
	@Deprecated
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
