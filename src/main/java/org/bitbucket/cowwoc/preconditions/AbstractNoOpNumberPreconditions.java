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
abstract class AbstractNoOpNumberPreconditions<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	implements NumberPreconditions<S, T>
{
	@Override
	@SuppressWarnings("unchecked")
	public S isGreaterThan(T value, String name)
	{
		return (S) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public S isGreaterThan(T value)
	{
		return (S) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		return (S) this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public S isGreaterThanOrEqualTo(T value)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isIn(Range<T> range)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isLessThan(T value, String name)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isLessThan(T value)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNegative()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotNegative()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotPositive()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotZero()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isPositive()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isZero()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S usingException(Class<? extends RuntimeException> exception)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isEqualTo(T value)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isEqualTo(T value, String name)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotEqualTo(T value)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotEqualTo(T value, String name)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isInstanceOf(Class<?> type)
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNull()
	{
		return (S) this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S isNotNull()
	{
		return (S) this;
	}
}
