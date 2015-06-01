/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;

/**
 * An implementation of NumberPreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpNumberPreconditions<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	implements NumberPreconditions<S, T>
{
	private final S self;

	/**
	 * Creates a new NoOpNumberPreconditions.
	 */
	@SuppressWarnings("unchecked")
	NoOpNumberPreconditions()
	{
		this.self = (S) this;
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		return self;
	}

	@Override
	public S isGreaterThan(T value)
	{
		return self;
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		return self;
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		return self;
	}

	@Override
	public S isIn(Range<T> range)
	{
		return self;
	}

	@Override
	public S isLessThan(T value, String name)
	{
		return self;
	}

	@Override
	public S isLessThan(T value)
	{
		return self;
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		return self;
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return self;
	}

	@Override
	public S isNegative()
	{
		return self;
	}

	@Override
	public S isNotNegative()
	{
		return self;
	}

	@Override
	public S isNotPositive()
	{
		return self;
	}

	@Override
	public S isNotZero()
	{
		return self;
	}

	@Override
	public S isPositive()
	{
		return self;
	}

	@Override
	public S isZero()
	{
		return self;
	}

	@Override
	public S usingException(Class<? extends RuntimeException> exception)
	{
		return self;
	}

	@Override
	public S isEqualTo(T value)
	{
		return self;
	}

	@Override
	public S isEqualTo(T value, String name)
	{
		return self;
	}

	@Override
	public S isNotEqualTo(T value)
	{
		return self;
	}

	@Override
	public S isNotEqualTo(T value, String name)
	{
		return self;
	}

	@Override
	public S isInstanceOf(Class<?> type)
	{
		return self;
	}

	@Override
	public S isNull()
	{
		return self;
	}

	@Override
	public S isNotNull()
	{
		return self;
	}
}
