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
		validator = validator.isGreaterThanOrEqualTo(value);
		return validationResult();
	}

	@Override
	public SizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		validator = validator.isGreaterThanOrEqualTo(value, name);
		return validationResult();
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value)
	{
		validator = validator.isGreaterThan(value);
		return validationResult();
	}

	@Override
	public SizeVerifier isGreaterThan(Integer value, String name)
	{
		validator = validator.isGreaterThan(value, name);
		return validationResult();
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value)
	{
		validator = validator.isLessThanOrEqualTo(value);
		return validationResult();
	}

	@Override
	public SizeVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		validator = validator.isLessThanOrEqualTo(value, name);
		return validationResult();
	}

	@Override
	public SizeVerifier isLessThan(Integer value)
	{
		validator = validator.isLessThan(value);
		return validationResult();
	}

	@Override
	public SizeVerifier isLessThan(Integer value, String name)
	{
		validator = validator.isLessThan(value, name);
		return validationResult();
	}

	@Override
	public SizeVerifier isNotPositive()
	{
		validator = validator.isNotPositive();
		return validationResult();
	}

	@Override
	public SizeVerifier isPositive()
	{
		validator = validator.isPositive();
		return validationResult();
	}

	@Override
	public SizeVerifier isNotZero()
	{
		validator = validator.isNotZero();
		return validationResult();
	}

	@Override
	public SizeVerifier isZero()
	{
		validator = validator.isZero();
		return validationResult();
	}

	@Override
	@Deprecated
	public SizeVerifier isNotNegative()
	{
		validator = validator.isNotNegative();
		return validationResult();
	}

	@Override
	@Deprecated
	public SizeVerifier isNegative()
	{
		validator = validator.isNegative();
		return validationResult();
	}

	@Override
	public SizeVerifier isBetween(Integer startInclusive, Integer endExclusive)
	{
		validator = validator.isBetween(startInclusive, endExclusive);
		return validationResult();
	}

	@Override
	public SizeVerifier isBetweenClosed(Integer startInclusive, Integer endInclusive)
	{
		validator = validator.isBetweenClosed(startInclusive, endInclusive);
		return validationResult();
	}

	@Override
	public SizeVerifier isEqualTo(Object expected)
	{
		validator = validator.isEqualTo(expected);
		return validationResult();
	}

	@Override
	public SizeVerifier isEqualTo(Object expected, String name)
	{
		validator = validator.isEqualTo(expected, name);
		return validationResult();
	}

	@Override
	public SizeVerifier isNotEqualTo(Object other)
	{
		validator = validator.isNotEqualTo(other);
		return validationResult();
	}

	@Override
	public SizeVerifier isNotEqualTo(Object other, String name)
	{
		validator = validator.isNotEqualTo(other, name);
		return validationResult();
	}

	@Override
	@Deprecated
	public SizeVerifier isNull()
	{
		validator = validator.isNull();
		return validationResult();
	}
}
