/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * An implementation of {@code BigDecimalVerifier} that does nothing.
 */
public final class NoOpBigDecimalVerifier
	extends NoOpNumberCapabilities<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBigDecimalVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected BigDecimalVerifier getThis()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return new NoOpBigDecimalPrecisionVerifier(config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> scale()
	{
		return new NoOpPrimitiveNumberVerifier<>(config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		return this;
	}
}
