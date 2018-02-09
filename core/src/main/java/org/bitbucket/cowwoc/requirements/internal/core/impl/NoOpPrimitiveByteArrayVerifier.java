/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveByteArrayVerifier;

/**
 * An implementation of {@link PrimitiveByteArrayVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveByteArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveByteArrayVerifier, Byte, byte[]>
	implements PrimitiveByteArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveByteArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveByteArrayVerifier getThis()
	{
		return this;
	}
}
