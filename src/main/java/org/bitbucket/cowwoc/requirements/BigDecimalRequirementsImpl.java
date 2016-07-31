/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Default implementation of BigDecimalRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalRequirementsImpl
	extends NumberRequirementsImpl<BigDecimalRequirements, BigDecimal>
	implements BigDecimalRequirements
{
	/**
	 * Creates new BigDecimalRequirementsImpl.
	 * <p>
	 * @param parameter         the value of the parameter
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalRequirementsImpl(BigDecimal parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, name, exceptionOverride);
	}

	@Override
	public BigDecimalRequirements usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new BigDecimalRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Override
	public BigDecimalRequirements isZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() == 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s must be zero\n" +
			"Expected: %s\n" +
			"Actual  : %s", name, 0, parameter));
	}

	@Override
	public BigDecimalRequirements isNotZero() throws IllegalArgumentException
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() != 0)
			return self;
		return throwException(IllegalArgumentException.class, String.format("%s may not be zero", name));
	}

	@Override
	public BigDecimalPrecisionRequirements precision()
	{
		return new BigDecimalPrecisionRequirementsImpl(parameter.precision(), name, exceptionOverride);
	}

	@Override
	public BigDecimalScaleRequirements scale()
	{
		return new BigDecimalScaleRequirementsImpl(parameter.scale(), name, exceptionOverride);
	}
}
