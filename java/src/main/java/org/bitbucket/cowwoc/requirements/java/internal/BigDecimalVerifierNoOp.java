/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * An implementation of {@code BigDecimalVerifier} that does nothing.
 */
public final class BigDecimalVerifierNoOp
	extends AbstractNumberVerifierNoOp<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public BigDecimalVerifierNoOp(Configuration config)
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
		return new BigDecimalPrecisionVerifierNoOp(config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> scale()
	{
		return new PrimitiveNumberVerifierNoOp<>(config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
