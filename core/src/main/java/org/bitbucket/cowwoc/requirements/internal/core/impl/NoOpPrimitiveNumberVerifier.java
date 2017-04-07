/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;

/**
 * An implementation of {@link PrimitiveNumberVerifier} that does nothing.
 *
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveNumberVerifier<T extends Number & Comparable<? super T>>
	extends NoOpPrimitiveNumberCapabilities<PrimitiveNumberVerifier<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveNumberVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveNumberVerifier<T> getThis()
	{
		return this;
	}
}
