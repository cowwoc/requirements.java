/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.CharacterVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;

/**
 * An implementation of {@link CharacterVerifier} that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpCharacterVerifier
	extends NoOpComparableCapabilities<CharacterVerifier, Character>
	implements CharacterVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpCharacterVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected CharacterVerifier getThis()
	{
		return this;
	}
}
