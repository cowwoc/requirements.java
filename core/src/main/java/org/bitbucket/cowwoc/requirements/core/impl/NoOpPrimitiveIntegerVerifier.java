/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;

/**
 * An implementation of {@code PrimitiveIntegerVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveIntegerVerifier
	extends NoOpPrimitiveIntegerCapabilities<PrimitiveIntegerVerifier, Integer>
	implements PrimitiveIntegerVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveIntegerVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveIntegerVerifier getThis()
	{
		return this;
	}
}
