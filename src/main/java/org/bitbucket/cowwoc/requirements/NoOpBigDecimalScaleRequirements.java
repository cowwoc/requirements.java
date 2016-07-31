/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.function.Consumer;

/**
 * An implementation of BigDecimalScaleRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpBigDecimalScaleRequirements implements BigDecimalScaleRequirements
{
	public static final NoOpBigDecimalScaleRequirements INSTANCE
		= new NoOpBigDecimalScaleRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpBigDecimalScaleRequirements()
	{
	}

	@Override
	public BigDecimalScaleRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalScaleRequirements isNull()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNull()
	{
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isolate(Consumer<BigDecimalScaleRequirements> consumer)
	{
		return this;
	}
}
