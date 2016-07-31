/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.function.Consumer;

/**
 * An implementation of ComparableRequirements that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpComparableRequirements<T extends Comparable<? super T>>
	implements ComparableRequirements<T>
{
	@Override
	public ComparableRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isIn(Range<T> range)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isNull()
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotNull()
	{
		return this;
	}

	@Override
	public ComparableRequirements<T> isolate(Consumer<ComparableRequirements<T>> consumer)
	{
		return this;
	}
}
