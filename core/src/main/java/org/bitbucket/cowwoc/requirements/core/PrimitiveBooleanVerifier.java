/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.BooleanCapabilities;

/**
 * Verifies a primitive {@code boolean} value.
 *
 * @author Gili Tzabari
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