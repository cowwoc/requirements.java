/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.ComparableCapabilities;

/**
 * Extendable implementation of {@link ComparableCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
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
	public S isGreaterThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isGreaterThan(T value)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThan(T value)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		return getThis();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		return getThis();
	}

	@Override
	public S isBetween(T min, T max)
	{
		return getThis();
	}
}
