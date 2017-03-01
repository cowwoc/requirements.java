/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;

/**
 * An implementation of {@link ClassVerifier} that does nothing.
 *
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public final class NoOpClassVerifier<T>
	extends NoOpObjectCapabilities<ClassVerifier<T>, Class<T>>
	implements ClassVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpClassVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected ClassVerifier<T> getThis()
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		return this;
	}
}
