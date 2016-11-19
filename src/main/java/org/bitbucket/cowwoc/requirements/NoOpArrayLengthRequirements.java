/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of ArrayLengthRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpArrayLengthRequirements implements ArrayLengthRequirements
{
	public static final NoOpArrayLengthRequirements INSTANCE = new NoOpArrayLengthRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpArrayLengthRequirements()
	{
	}

	@Override
	public ArrayLengthRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public ArrayLengthRequirements isNull()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNegative()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotZero()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isPositive()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isZero()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Deprecated
	@Override
	public ArrayLengthRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isIn(Integer first, Integer last)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotNull()
	{
		return this;
	}

	@Override
	public ArrayLengthRequirements isolate(Consumer<ArrayLengthRequirements> consumer)
	{
		return this;
	}

}
