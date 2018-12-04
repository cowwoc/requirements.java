/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;

/**
 * Extendable implementation of {@link ComparableCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class NoOpComparableCapabilities<S, T extends Comparable<? super T>>
	extends NoOpObjectCapabilities<S, T>
	implements ComparableCapabilities<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpComparableCapabilities(Configuration config)
	{
		super(config);
	}

	@Override
	public S isGreaterThan(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThan(String name, T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(String name, T value)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThan(String name, T value)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(String name, T value)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(T expected)
	{
		return getThis();
	}

	@Override
	public S isComparableTo(String name, T expected)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(T other)
	{
		return getThis();
	}

	@Override
	public S isNotComparableTo(String name, T other)
	{
		return getThis();
	}

	@Override
	public S isBetween(T startInclusive, T endExclusive)
	{
		return getThis();
	}

	@Override
	public S isBetweenClosed(T startInclusive, T endInclusive)
	{
		return getThis();
	}
}
