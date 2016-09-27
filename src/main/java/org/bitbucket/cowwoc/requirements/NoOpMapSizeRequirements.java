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
 * An implementation of MapSizeRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpMapSizeRequirements implements MapSizeRequirements
{
	public static final NoOpMapSizeRequirements INSTANCE = new NoOpMapSizeRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpMapSizeRequirements()
	{
	}

	@Override
	public MapSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public MapSizeRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Deprecated
	@Override
	public MapSizeRequirements isNull()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNegative()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotZero()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isPositive()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isZero()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public MapSizeRequirements isNotNull()
	{
		return this;
	}

	@Override
	public MapSizeRequirements isolate(Consumer<MapSizeRequirements> consumer)
	{
		return this;
	}
}
