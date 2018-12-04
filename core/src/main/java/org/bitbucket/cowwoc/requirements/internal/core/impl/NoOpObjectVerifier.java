/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;

/**
 * An implementation of {@link ObjectVerifier} that does nothing.
 *
 * @param <T> the type of the value
 */
public final class NoOpObjectVerifier<T>
	extends NoOpObjectCapabilities<ObjectVerifier<T>, T>
	implements ObjectVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpObjectVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected NoOpObjectVerifier<T> getThis()
	{
		return this;
	}
}
