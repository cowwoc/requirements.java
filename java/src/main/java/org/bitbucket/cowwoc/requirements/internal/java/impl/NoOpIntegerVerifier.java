/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.java.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;

/**
 * An implementation of {@link IntegerVerifier} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class NoOpIntegerVerifier<T extends Number & Comparable<? super T>>
	extends NoOpIntegerCapabilities<IntegerVerifier<T>, T>
	implements IntegerVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpIntegerVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected IntegerVerifier<T> getThis()
	{
		return this;
	}
}
