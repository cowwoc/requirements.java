/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveDoubleArrayVerifier;

/**
 * An implementation of {@link PrimitiveDoubleArrayVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveDoubleArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveDoubleArrayVerifier, Double, double[]>
	implements PrimitiveDoubleArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveDoubleArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveDoubleArrayVerifier getThis()
	{
		return this;
	}
}
