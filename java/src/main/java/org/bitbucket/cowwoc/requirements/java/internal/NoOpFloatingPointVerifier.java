/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;

/**
 * An implementation of {@code FloatingPointVerifier} that does nothing.
 *
 * @param <T> the type of the floating-point number
 */
public final class NoOpFloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends NoOpFloatingPointCapabilities<FloatingPointVerifier<T>, T>
	implements FloatingPointVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpFloatingPointVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected FloatingPointVerifier<T> getThis()
	{
		return this;
	}
}
