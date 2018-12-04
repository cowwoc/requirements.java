/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;

/**
 * An implementation of {@link PrimitiveIntegerVerifier} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class NoOpPrimitiveIntegerVerifier<T extends Number & Comparable<? super T>>
	extends NoOpIntegerCapabilities<PrimitiveIntegerVerifier<T>, T>
	implements PrimitiveIntegerVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveIntegerVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveIntegerVerifier<T> getThis()
	{
		return this;
	}
}
