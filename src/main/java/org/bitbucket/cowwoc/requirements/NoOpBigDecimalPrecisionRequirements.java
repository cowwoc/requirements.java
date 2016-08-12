/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of BigDecimalPrecisionRequirements that does nothing.
 *
 * @author Gili Tzabari
 */
final class NoOpBigDecimalPrecisionRequirements implements BigDecimalPrecisionRequirements
{
	public static final NoOpBigDecimalPrecisionRequirements INSTANCE
		= new NoOpBigDecimalPrecisionRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpBigDecimalPrecisionRequirements()
	{
	}

	@Override
	public BigDecimalPrecisionRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements withContext(Map<String, Object> context)
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isZero()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNotPositive()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNegative()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNull()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotNull()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isolate(
		Consumer<BigDecimalPrecisionRequirements> consumer)
	{
		return this;
	}

}
