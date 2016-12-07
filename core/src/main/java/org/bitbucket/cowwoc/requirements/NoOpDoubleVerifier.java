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
 * An implementation of DoubleVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
enum NoOpDoubleVerifier implements DoubleVerifier
{
	INSTANCE;

	@Override
	public DoubleVerifier isNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isFinite()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotFinite()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNegative()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotZero()
	{
		return this;
	}

	@Override
	public DoubleVerifier isPositive()
	{
		return this;
	}

	@Override
	public DoubleVerifier isZero()
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isIn(Double first, Double last)
	{
		return this;
	}

	@Override
	public DoubleVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public DoubleVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public DoubleVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isIn(Collection<Double> collection)
	{
		return this;
	}

	@Override
	public DoubleVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNull()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNull()
	{
		return this;
	}

	@Override
	public DoubleVerifier isolate(Consumer<DoubleVerifier> consumer)
	{
		return this;
	}

}
