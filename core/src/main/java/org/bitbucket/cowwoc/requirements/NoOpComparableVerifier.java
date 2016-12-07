/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * An implementation of ComparableVerifier that does nothing.
 * <p>
 * @author Gili Tzabari
 */
final class NoOpComparableVerifier<T extends Comparable<? super T>>
	implements ComparableVerifier<T>
{
	@Override
	public ComparableVerifier<T> withException(Class<? extends RuntimeException> exception)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> addContext(String key, Object value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> withContext(List<Entry<String, Object>> context)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isIn(T first, T last)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value, String name)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isIn(Collection<T> collection)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNull()
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotNull()
	{
		return this;
	}

	@Override
	public ComparableVerifier<T> isolate(Consumer<ComparableVerifier<T>> consumer)
	{
		return this;
	}
}
