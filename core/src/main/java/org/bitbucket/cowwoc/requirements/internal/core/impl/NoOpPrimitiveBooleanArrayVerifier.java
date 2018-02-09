/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveBooleanArrayVerifier;

/**
 * An implementation of {@link PrimitiveBooleanArrayVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveBooleanArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveBooleanArrayVerifier, Boolean, boolean[]>
	implements PrimitiveBooleanArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveBooleanArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveBooleanArrayVerifier getThis()
	{
		return this;
	}
}
