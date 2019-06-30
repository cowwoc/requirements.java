/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

/**
 * A {@code PrimitiveNumberVerifier} that does nothing.
 *
 * @param <T> the type of the value
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
