/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;

/**
 * Verifies the requirements of a {@code char} value.
 */
public interface PrimitiveCharacterVerifier extends ComparableCapabilities<PrimitiveCharacterVerifier, Character>
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
