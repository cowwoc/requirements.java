/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.extension.ExtensibleNumberValidator;

import java.util.List;

/**
 * An {@code ExtensibleNumberValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractNumberValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractComparableValidatorNoOp<S, T>
	implements ExtensibleNumberValidator<S, T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	protected AbstractNumberValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
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
