/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.NumberCapabilities;

/**
 * Extendable implementation of {@link NumberCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class NoOpNumberCapabilities<S, T extends Number & Comparable<? super T>>
	extends NoOpComparableCapabilities<S, T>
	implements NumberCapabilities<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpNumberCapabilities(Configuration config)
	{
		super(config);
	}

	@Override
	public S isNegative()
	{
		return getThis();
	}

	@Override
	public S isNotNegative()
	{
		return getThis();
	}

	@Override
	public S isZero()
	{
		return getThis();
	}

	@Override
	public S isNotZero()
	{
		return getThis();
	}

	@Override
	public S isPositive()
	{
		return getThis();
	}

	@Override
	public S isNotPositive()
	{
		return getThis();
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		return getThis();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		return getThis();
	}
}
