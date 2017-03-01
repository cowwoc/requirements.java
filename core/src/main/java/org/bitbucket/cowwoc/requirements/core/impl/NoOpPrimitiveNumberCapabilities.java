/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveNumberCapabilities;

/**
 * Extendable implementation of {@link PrimitiveNumberVerifier} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class NoOpPrimitiveNumberCapabilities<S, T extends Number & Comparable<? super T>>
	extends NoOpNumberCapabilities<S, T>
	implements PrimitiveNumberCapabilities<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveNumberCapabilities(Configuration config)
	{
		super(config);
	}

	@Deprecated
	@Override
	public S isNegative()
	{
		return super.isNegative();
	}

	@Deprecated
	@Override
	public S isNotNegative()
	{
		return super.isNotNegative();
	}

	@Deprecated
	@Override
	public S isZero()
	{
		return super.isZero();
	}

	@Deprecated
	@Override
	public S isNotZero()
	{
		return super.isNotZero();
	}

	@Deprecated
	@Override
	public S isPositive()
	{
		return super.isPositive();
	}

	@Deprecated
	@Override
	public S isNotPositive()
	{
		return super.isNotPositive();
	}
}
