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
 * An implementation of ContainerSizeVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpContainerSizeVerifier implements ContainerSizeVerifier
{
	public static final NoOpContainerSizeVerifier INSTANCE = new NoOpContainerSizeVerifier();

	/**
	 * Prevent construction.
	 */
	private NoOpContainerSizeVerifier()
	{
	}

	@Override
	public ContainerSizeVerifier withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	@Deprecated
	public ContainerSizeVerifier isNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotZero()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isZero()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isIn(Integer first, Integer last)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isolate(Consumer<ContainerSizeVerifier> consumer)
	{
		return this;
	}
}
