/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;

/**
 * Extendable implementation of {@link FloatingPointCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class NoOpFloatingPointCapabilities<S, T extends Number & Comparable<? super T>>
	extends NoOpNumberCapabilities<S, T>
	implements FloatingPointCapabilities<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpFloatingPointCapabilities(Configuration config)
	{
		super(config);
	}

	@Override
	public S isNumber()
	{
		return getThis();
	}

	@Override
	public S isNotNumber()
	{
		return getThis();
	}

	@Override
	public S isFinite()
	{
		return getThis();
	}

	@Override
	public S isNotFinite()
	{
		return getThis();
	}
}
