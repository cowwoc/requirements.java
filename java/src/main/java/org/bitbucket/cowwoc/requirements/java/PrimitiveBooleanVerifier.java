/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.BooleanCapabilities;

/**
 * Verifies the requirements of a primitive {@code boolean} value.
 */
public interface PrimitiveBooleanVerifier extends BooleanCapabilities<PrimitiveBooleanVerifier>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveBooleanVerifier isNull();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value cannot be null
	 */
	@Override
	@Deprecated
	PrimitiveBooleanVerifier isNotNull();
}
