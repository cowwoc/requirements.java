/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;

/**
 * Default implementation of {@code ExtensibleNumberVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 * @param <T> the type of the value being verified
 */
public abstract class AbstractNumberVerifier
	<S, V extends ExtensibleNumberValidator<V, T>, T extends Number & Comparable<? super T>>
	extends AbstractComparableVerifier<S, V, T>
	implements ExtensibleNumberVerifier<S, T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractNumberVerifier(V validator)
	{
		super(validator);
	}

	@Override
	public S isNegative()
	{
		validator.isNegative();
		return validationResult();
	}

	@Override
	public S isNotNegative()
	{
		validator.isNotNegative();
		return validationResult();
	}

	@Override
	public S isZero()
	{
		validator.isZero();
		return validationResult();
	}

	@Override
	public S isNotZero()
	{
		validator.isNotZero();
		return validationResult();
	}

	@Override
	public S isPositive()
	{
		validator.isPositive();
		return validationResult();
	}

	@Override
	public S isNotPositive()
	{
		validator.isNotPositive();
		return validationResult();
	}

	@Override
	public S isWholeNumber()
	{
		validator.isWholeNumber();
		return validationResult();
	}

	@Override
	public S isNotWholeNumber()
	{
		validator.isNotWholeNumber();
		return validationResult();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		validator.isMultipleOf(divisor);
		return validationResult();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		validator.isMultipleOf(divisor, name);
		return validationResult();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		validator.isNotMultipleOf(divisor);
		return validationResult();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		validator.isNotMultipleOf(divisor, name);
		return validationResult();
	}
}
