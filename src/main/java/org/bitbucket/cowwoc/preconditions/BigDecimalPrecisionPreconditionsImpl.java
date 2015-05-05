/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Default implementation of BigDecimalPrecisionPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalPrecisionPreconditionsImpl
	extends PrimitiveIntegerPreconditionsImpl<BigDecimalPrecisionPreconditions>
	implements BigDecimalPrecisionPreconditions,
	PrimitiveIntegerPreconditions<BigDecimalPrecisionPreconditions>
{
	/**
	 * Creates new BigDecimalPrecisionPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of BigDecimal.precision()
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if name or exceptionOverride are null
	 * @throws IllegalArgumentException if name is empty
	 */
	BigDecimalPrecisionPreconditionsImpl(int parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name + ".precision()", exceptionOverride);
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionPreconditions isZero() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalPrecisionPreconditions isNotZero() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionPreconditions isNotPositive() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalPrecisionPreconditions isPositive() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Override
	public BigDecimalPrecisionPreconditions isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionPreconditions isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be negative", name));
	}
}
