/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * An implementation of {@code BigDecimalPrecisionVerifier} that does nothing.
 */
public final class BigDecimalPrecisionVerifierNoOp
	extends AbstractPrimitiveNumberVerifierNoOp<BigDecimalPrecisionVerifier, Integer>
	implements BigDecimalPrecisionVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public BigDecimalPrecisionVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected BigDecimalPrecisionVerifier getThis()
	{
		return this;
	}
}
