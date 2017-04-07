/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveFloatingPointVerifier;

/**
 * An implementation of {@link PrimitiveFloatingPointVerifier} that does nothing.
 *
 * @param <T> the type of the floating-point number
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveFloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends NoOpFloatingPointCapabilities<PrimitiveFloatingPointVerifier<T>, T>
	implements PrimitiveFloatingPointVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveFloatingPointVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<T> getThis()
	{
		return this;
	}
}
