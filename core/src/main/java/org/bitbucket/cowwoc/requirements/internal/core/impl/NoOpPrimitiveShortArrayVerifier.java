/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveShortArrayVerifier;

/**
 * An implementation of {@link PrimitiveShortArrayVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveShortArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveShortArrayVerifier, Short, short[]>
	implements PrimitiveShortArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveShortArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveShortArrayVerifier getThis()
	{
		return this;
	}
}
