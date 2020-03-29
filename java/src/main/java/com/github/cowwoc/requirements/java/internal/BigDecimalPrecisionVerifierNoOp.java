/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * A {@code BigDecimalPrecisionVerifier} that does nothing.
 */
public final class BigDecimalPrecisionVerifierNoOp
	extends AbstractPrimitiveNumberVerifierNoOp<BigDecimalPrecisionVerifier, Integer>
	implements BigDecimalPrecisionVerifier
{
	private static final BigDecimalPrecisionVerifierNoOp INSTANCE = new BigDecimalPrecisionVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static BigDecimalPrecisionVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private BigDecimalPrecisionVerifierNoOp()
	{
	}

	@Override
	protected BigDecimalPrecisionVerifier getThis()
	{
		return this;
	}
}
