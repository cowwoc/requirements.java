/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.FloatingPointVerifier;

/**
 * An implementation of {@link FloatingPointVerifier} that does nothing.
 *
 * @param <T> the type of the floating-point number
 * @author Gili Tzabari
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
