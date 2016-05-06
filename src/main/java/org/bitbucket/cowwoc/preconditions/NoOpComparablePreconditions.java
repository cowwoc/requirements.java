/*
 * Copyright 2016 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.function.Consumer;

/**
 * An implementation of ComparablePreconditions that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpComparablePreconditions<T extends Comparable<? super T>>
	implements ComparablePreconditions<T>
{
	@Override
	public ComparablePreconditions<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> usingException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isNull()
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isNotNull()
	{
		return this;
	}

	@Override
	public ComparablePreconditions<T> isolate(Consumer<ComparablePreconditions<T>> consumer)
	{
		return this;
	}

}
