/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;

/**
 * An implementation of {@link BigDecimalPrecisionVerifier} that does nothing.
 */
public final class NoOpBigDecimalPrecisionVerifier
	extends NoOpPrimitiveNumberCapabilities<BigDecimalPrecisionVerifier, Integer>
	implements BigDecimalPrecisionVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBigDecimalPrecisionVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected BigDecimalPrecisionVerifier getThis()
	{
		return this;
	}
}
