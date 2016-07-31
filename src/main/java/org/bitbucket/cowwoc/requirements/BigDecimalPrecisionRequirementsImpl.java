/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;

/**
 * Default implementation of BigDecimalPrecisionRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalPrecisionRequirementsImpl
	extends PrimitiveIntegerRequirementsImpl<BigDecimalPrecisionRequirements>
	implements BigDecimalPrecisionRequirements
{
	private static String getName(String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		return name + ".precision()";
	}

	/**
	 * Creates new BigDecimalPrecisionRequirementsImpl.
	 * <p>
	 * @param parameter         the value of BigDecimal.precision()
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalPrecisionRequirementsImpl(int parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, getName(name), exceptionOverride);
	}

	@Override
	public BigDecimalPrecisionRequirements usingException(
		Class<? extends RuntimeException> exception)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return self;
		return new BigDecimalPrecisionRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isZero() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalPrecisionRequirements isNotZero() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNotPositive() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalPrecisionRequirements isPositive() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be negative", name));
	}
}
