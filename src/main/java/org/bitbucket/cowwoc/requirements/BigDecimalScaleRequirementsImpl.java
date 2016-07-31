/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Objects;

/**
 * Default implementation of BigDecimalScaleRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalScaleRequirementsImpl
	extends PrimitiveIntegerRequirementsImpl<BigDecimalScaleRequirements>
	implements BigDecimalScaleRequirements
{
	private static String getName(String name) throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		return name + ".scale()";
	}

	/**
	 * Creates new BigDecimalScaleRequirementsImpl.
	 * <p>
	 * @param parameter         the value of BigDecimal.scale()
	 * @param name              the name of the parameter
	 * @param exceptionOverride the type of exception to throw, null to disable the override
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalScaleRequirementsImpl(int parameter, String name,
		Class<? extends RuntimeException> exceptionOverride)
		throws NullPointerException, IllegalArgumentException
	{
		super(parameter, getName(name), exceptionOverride);
	}

	@Override
	public BigDecimalScaleRequirements usingException(
		Class<? extends RuntimeException> exceptionOverride)
	{
		if (Objects.equals(exceptionOverride, this.exceptionOverride))
			return this;
		return new BigDecimalScaleRequirementsImpl(parameter, name, exceptionOverride);
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isZero() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalScaleRequirements isNotZero() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNotPositive() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalScaleRequirements isPositive() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Override
	public BigDecimalScaleRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return self;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNegative() throws IllegalArgumentException
	{
		throw new AssertionError(String.format("%s can never be negative", name));
	}
}
