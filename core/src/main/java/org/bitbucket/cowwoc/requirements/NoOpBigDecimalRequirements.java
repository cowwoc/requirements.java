/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of {@code BigDecimalRequirements} that does nothing.
 *
 * @author Gili Tzabari
 */
final class NoOpBigDecimalRequirements implements BigDecimalRequirements
{
	public static final NoOpBigDecimalRequirements INSTANCE = new NoOpBigDecimalRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpBigDecimalRequirements()
	{
	}

	@Override
	public BigDecimalRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements precision()
	{
		return NoOpBigDecimalPrecisionRequirements.INSTANCE;
	}

	@Override
	public BigDecimalScaleRequirements scale()
	{
		return NoOpBigDecimalScaleRequirements.INSTANCE;
	}

	@Override
	public BigDecimalRequirements isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isZero()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(BigDecimal first, BigDecimal last)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(Collection<BigDecimal> collection)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNull()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNull()
	{
		return this;
	}

	@Override
	public BigDecimalRequirements isolate(Consumer<BigDecimalRequirements> consumer)
	{
		return this;
	}
}
