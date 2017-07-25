/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.ComparableCapabilities;

/**
 * Verifies a primitive {@code char} value.
 *
 * @author Gili Tzabari
 */
public interface PrimitiveCharacterVerifier
	extends ComparableCapabilities<PrimitiveCharacterVerifier, Character>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveCharacterVerifier isNull();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveCharacterVerifier isNotNull();
}
