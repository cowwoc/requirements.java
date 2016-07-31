/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Map;
import java.util.function.Consumer;

/**
 * An implementation of NumberRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpNumberRequirements<T extends Number & Comparable<? super T>>
	implements NumberRequirements<T>
{
	/**
	 * Creates a new NoOpNumberRequirements.
	 */
	NoOpNumberRequirements()
	{
	}

	@Override
	public NumberRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> withContext(Map<String, Object> context)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isIn(Range<T> range)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNegative()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotNegative()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotPositive()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotZero()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isPositive()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isZero()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNull()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isNotNull()
	{
		return this;
	}

	@Override
	public NumberRequirements<T> isolate(Consumer<NumberRequirements<T>> consumer)
	{
		return this;
	}
}
