/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * A {@code BigDecimalVerifier} that does nothing.
 */
public final class BigDecimalVerifierNoOp
	extends AbstractNumberVerifierNoOp<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	private static final BigDecimalVerifierNoOp INSTANCE = new BigDecimalVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static BigDecimalVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private BigDecimalVerifierNoOp()
	{
	}

	@Override
	protected BigDecimalVerifier getThis()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return BigDecimalPrecisionVerifierNoOp.getInstance();
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
		return PrimitiveNumberVerifierNoOp.getInstance();
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
