/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;

/**
 * A {@code PrimitiveLongArrayVerifier} that does nothing.
 */
public final class NoOpPrimitiveLongArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveLongArrayVerifier, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveLongArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveLongArrayVerifier getThis()
	{
		return this;
	}
}
