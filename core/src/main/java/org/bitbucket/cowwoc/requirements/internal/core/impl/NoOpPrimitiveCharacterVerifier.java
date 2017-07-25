/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveCharacterVerifier;

/**
 * An implementation of {@link PrimitiveCharacterVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveCharacterVerifier
	extends NoOpComparableCapabilities<PrimitiveCharacterVerifier, Character>
	implements PrimitiveCharacterVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveCharacterVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveCharacterVerifier getThis()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNotNull()
	{
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNull()
	{
		return super.isNull();
	}
}
