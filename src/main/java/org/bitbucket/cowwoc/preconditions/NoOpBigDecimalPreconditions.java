/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;
import java.math.BigDecimal;

/**
 * An implementation of BigDecimalPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpBigDecimalPreconditions implements BigDecimalPreconditions
{
	INSTANCE;

	@Override
	public BigDecimalPreconditions hasPrecisionIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions hasScaleIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isGreaterThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isGreaterThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isGreaterThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isIn(Range<BigDecimal> range)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isLessThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isLessThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isLessThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isLessThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isZero()
	{
		return this;
	}

	@Override
	public <E extends RuntimeException> BigDecimalPreconditions using(Class<E> exception)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNull()
	{
		return this;
	}

	@Override
	public BigDecimalPreconditions isNotNull()
	{
		return this;
	}
}
