/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveCharacterArrayVerifier;

/**
 * An implementation of {@link PrimitiveCharacterArrayVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveCharacterArrayVerifier
	extends NoOpArrayCapabilities<PrimitiveCharacterArrayVerifier, Character, char[]>
	implements PrimitiveCharacterArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveCharacterArrayVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveCharacterArrayVerifier getThis()
	{
		return this;
	}
}
