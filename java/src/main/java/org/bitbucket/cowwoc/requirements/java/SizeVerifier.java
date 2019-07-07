/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of the size of a collection.
 */
public interface SizeVerifier
	extends ExtensiblePrimitiveVerifier<SizeVerifier, Integer>,
	ExtensibleNumberVerifier<SizeVerifier, Integer>
{
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	SizeVerifier isNegative();

	/**
	 * {@inheritDoc}
	 *
	 * @deprecated the actual value is always positive
	 */
	@Override
	@Deprecated
	SizeVerifier isNotNegative();
}
