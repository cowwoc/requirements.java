/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;

/**
 * An implementation of {@code BigDecimalPrecisionVerifier} that does nothing.
 *
 * @author Gili Tzabari
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

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
	{
		return super.isNegative();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		return super.isZero();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		return super.isNotPositive();
	}
}
