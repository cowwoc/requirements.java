/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.CharacterVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;

/**
 * Default implementation of {@link CharacterVerifier}.
 *
 * @author Gili Tzabari
 */
public class CharacterVerifierImpl
	extends ComparableCapabilitiesImpl<CharacterVerifier, Character>
	implements CharacterVerifier
{
	/**
	 * Creates a new CharacterVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public CharacterVerifierImpl(ApplicationScope scope, String name, Character actual,
		Configuration config)
	{
		super(scope, name, actual, config);
	}
}
