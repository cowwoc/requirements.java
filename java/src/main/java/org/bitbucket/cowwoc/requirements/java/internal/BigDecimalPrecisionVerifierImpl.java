/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code BigDecimalPrecisionVerifier}.
 */
public final class BigDecimalPrecisionVerifierImpl
	extends AbstractNumberVerifier<BigDecimalPrecisionVerifier, BigDecimalPrecisionValidator, Integer>
	implements BigDecimalPrecisionVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public BigDecimalPrecisionVerifierImpl(BigDecimalPrecisionValidator validator)
	{
		super(validator);
	}

	@Override
	protected BigDecimalPrecisionVerifier getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isZero()
	{
		return super.isZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isNotZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotPositive();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isPositive();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}
}
