/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Default implementation of BigDecimalScalePreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalScalePreconditionsImpl
	extends PrimitiveIntegerPreconditionsImpl<BigDecimalScalePreconditions>
	implements BigDecimalScalePreconditions,
	PrimitiveIntegerPreconditions<BigDecimalScalePreconditions>
{
	/**
	 * Creates new BigDecimalScalePreconditionsImpl.
	 * <p>
	 * @param parameter         the value of BigDecimal.scale()
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalScalePreconditionsImpl(int parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name + ".scale()", exceptionOverride);
	}

	@Deprecated
	@Override
	public BigDecimalScalePreconditions isZero() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalScalePreconditions isNotZero() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalScalePreconditions isNotPositive() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalScalePreconditions isPositive() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Override
	public BigDecimalScalePreconditions isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalScalePreconditions isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be negative", name));
	}
}
