/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleBooleanVerifier;

/**
 * Verifies the requirements of a primitive {@code boolean} value.
 */
public interface PrimitiveBooleanVerifier extends ExtensibleBooleanVerifier<PrimitiveBooleanVerifier>
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
