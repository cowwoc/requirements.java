/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.IntegerCapabilities;

/**
 * Extendable implementation of {@link IntegerCapabilities} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class NoOpIntegerCapabilities<S, T extends Number & Comparable<? super T>>
	extends NoOpNumberCapabilities<S, T>
	implements IntegerCapabilities<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpIntegerCapabilities(Configuration config)
	{
		super(config);
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
}
