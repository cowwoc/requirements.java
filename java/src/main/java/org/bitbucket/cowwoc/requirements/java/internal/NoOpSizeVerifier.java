/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;

/**
 * A {@code SizeVerifier} that does nothing.
 */
public final class NoOpSizeVerifier
	extends NoOpPrimitiveNumberCapabilities<SizeVerifier, Integer>
	implements SizeVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpSizeVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected SizeVerifier getThis()
	{
		return this;
	}
}
