/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Default implementation of BigDecimalPreconditions.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalPreconditionsImpl
	extends NumberPreconditionsImpl<BigDecimalPreconditions, BigDecimal>
	implements BigDecimalPreconditions
{
	/**
	 * Creates new BigDecimalPreconditionsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} or {@code exceptionOverride} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalPreconditionsImpl(BigDecimal parameter, String name,
		Optional<Class<? extends RuntimeException>> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public BigDecimalPreconditions isZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() == 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be zero\n" +
			"Expected: %s\n" +
			"Actual  : %s", name, 0, parameter));
	}

	@Override
	public BigDecimalPreconditions isNotZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() != 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s may not be zero", name));
	}

	@Override
	public BigDecimalPrecisionPreconditions precision()
	{
		return new BigDecimalPrecisionPreconditionsImpl(parameter.precision(), name, exceptionOverride);
	}

	@Override
	public BigDecimalScalePreconditions scale()
	{
		return new BigDecimalScalePreconditionsImpl(parameter.scale(), name, exceptionOverride);
	}
}
