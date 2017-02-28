/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveIntegerCapabilities;

/**
 * Extendable implementation of {@code PrimitiveIntegerVerifier} that does nothing.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class NoOpPrimitiveIntegerCapabilities<S, T>
	extends NoOpNumberCapabilities<S, Integer>
	implements PrimitiveIntegerCapabilities<S>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveIntegerCapabilities(Configuration config)
	{
		super(config);
	}
}
