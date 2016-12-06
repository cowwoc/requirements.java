/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of ContainerSizeRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpContainerSizeRequirements implements ContainerSizeRequirements
{
	public static final NoOpContainerSizeRequirements INSTANCE = new NoOpContainerSizeRequirements();

	/**
	 * Prevent construction.
	 */
	private NoOpContainerSizeRequirements()
	{
	}

	@Override
	public ContainerSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public ContainerSizeRequirements isNull()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotZero()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isZero()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isIn(Integer first, Integer last)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isNotNull()
	{
		return this;
	}

	@Override
	public ContainerSizeRequirements isolate(Consumer<ContainerSizeRequirements> consumer)
	{
		return this;
	}
}
