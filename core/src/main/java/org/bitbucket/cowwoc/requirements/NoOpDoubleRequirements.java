/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of DoubleRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpDoubleRequirements implements DoubleRequirements
{
	INSTANCE;

	@Override
	public DoubleRequirements isNumber()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotNumber()
	{
		return this;
	}

	@Override
	public DoubleRequirements isFinite()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotFinite()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNegative()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotZero()
	{
		return this;
	}

	@Override
	public DoubleRequirements isPositive()
	{
		return this;
	}

	@Override
	public DoubleRequirements isZero()
	{
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isIn(Double first, Double last)
	{
		return this;
	}

	@Override
	public DoubleRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public DoubleRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public DoubleRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleRequirements isIn(Collection<Double> collection)
	{
		return this;
	}

	@Override
	public DoubleRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public DoubleRequirements isNull()
	{
		return this;
	}

	@Override
	public DoubleRequirements isNotNull()
	{
		return this;
	}

	@Override
	public DoubleRequirements isolate(Consumer<DoubleRequirements> consumer)
	{
		return this;
	}

}
