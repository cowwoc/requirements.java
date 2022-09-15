/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of a {@code SizeVerifier}.
 */
public final class SizeVerifierImpl
	extends AbstractNumberVerifier<SizeVerifier, SizeValidator, Integer>
	implements SizeVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public SizeVerifierImpl(SizeValidator validator)
	{
		super(validator);
	}

	@Override
	protected SizeVerifier getThis()
	{
		return this;
	}

	@Override
	public SizeVerifier isGreaterThanOrEqualTo(Integer value)
	{
		validator.isGreaterThanOrEqualTo(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		validator.isGreaterThanOrEqualTo(value, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value)
	{
		validator.isGreaterThan(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value, String name)
	{
		validator.isGreaterThan(value, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value)
	{
		validator.isLessThanOrEqualTo(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		validator.isLessThanOrEqualTo(value, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isLessThan(Integer value)
	{
		validator.isLessThan(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isLessThan(Integer value, String name)
	{
		validator.isLessThan(value, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isNotPositive()
	{
		validator.isNotPositive();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isPositive()
	{
		validator.isPositive();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isNotZero()
	{
		validator.isNotZero();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isZero()
	{
		validator.isZero();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	@Deprecated
	public SizeVerifier isNotNegative()
	{
		validator.isNotNegative();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	@Deprecated
	public SizeVerifier isNegative()
	{
		validator.isNegative();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isBetween(Integer startInclusive, Integer endExclusive)
	{
		validator.isBetween(startInclusive, endExclusive);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		validator.isBetweenClosed(startInclusive, endInclusive);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isEqualTo(Object expected)
	{
		validator.isEqualTo(expected);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isEqualTo(Object expected, String name)
	{
		validator.isEqualTo(expected, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isNotEqualTo(Object other)
	{
		validator.isNotEqualTo(other);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public SizeVerifier isNotEqualTo(Object other, String name)
	{
		validator.isNotEqualTo(other, name);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	@Deprecated
	public SizeVerifier isNull()
	{
		validator.isNull();
		return validationResult(IllegalArgumentException.class);
	}
}
