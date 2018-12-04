/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveFloatArrayVerifier;

/**
 * An implementation of {@link PrimitiveFloatArrayVerifier} that does nothing.
 */
public final class NoOpPrimitiveFloatArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveFloatArrayVerifier, Float, float[]>
	implements PrimitiveFloatArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveFloatArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveFloatArrayVerifier getThis()
	{
		return this;
	}
}
