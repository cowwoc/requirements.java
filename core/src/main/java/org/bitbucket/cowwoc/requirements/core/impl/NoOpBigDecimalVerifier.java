/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;

/**
 * An implementation of {@code BigDecimalVerifier} that does nothing.
 *
 * @author Gili Tzabari
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
