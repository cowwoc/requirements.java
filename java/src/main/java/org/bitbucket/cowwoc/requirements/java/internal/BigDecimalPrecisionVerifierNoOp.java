/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * An implementation of {@code BigDecimalPrecisionVerifier} that does nothing.
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
