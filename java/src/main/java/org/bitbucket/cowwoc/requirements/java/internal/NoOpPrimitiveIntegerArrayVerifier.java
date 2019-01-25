/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;

/**
 * An implementation of {@link PrimitiveIntegerArrayVerifier} that does nothing.
 */
public final class NoOpPrimitiveIntegerArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveIntegerArrayVerifier, Integer, int[]>
	implements PrimitiveIntegerArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveIntegerArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveIntegerArrayVerifier getThis()
	{
		return this;
	}
}
