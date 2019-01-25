/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.NumberVerifier;

/**
 * An implementation of {@link NumberVerifier} that does nothing.
 *
 * @param <T> the type of the value
 */
public final class NoOpNumberVerifier<T extends Number & Comparable<? super T>>
	extends NoOpNumberCapabilities<NumberVerifier<T>, T>
	implements NumberVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpNumberVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected NumberVerifier<T> getThis()
	{
		return this;
	}
}
