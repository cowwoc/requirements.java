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
 * An implementation of CollectionSizeRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpCollectionSizeRequirements implements CollectionSizeRequirements
{
	public static final NoOpCollectionSizeRequirements INSTANCE
		= new NoOpCollectionSizeRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpCollectionSizeRequirements()
	{
	}

	@Override
	public CollectionSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public CollectionSizeRequirements isNull()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNegative()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotZero()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isPositive()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isZero()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isIn(Range<Integer> range)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotNull()
	{
		return this;
	}

	@Override
	public CollectionSizeRequirements isolate(Consumer<CollectionSizeRequirements> consumer)
	{
		return this;
	}

}
