/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;

/**
 * An implementation of {@link ArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 */
public final class NoOpArrayVerifier<E>
	extends NoOpArrayCapabilities<ArrayVerifier<E>, E, E[]>
	implements ArrayVerifier<E>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected ArrayVerifier<E> getThis()
	{
		return this;
	}
}
