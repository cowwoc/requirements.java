/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

import java.math.BigDecimal;

/**
 * An implementation of {@code PrimitiveNumberVerifier} for a {@link BigDecimal}'s scale.
 */
public final class BigDecimalScaleVerifierImpl
	extends AbstractNumberVerifier<PrimitiveNumberVerifier<Integer>, BigDecimalPrecisionValidator, Integer>
	implements PrimitiveNumberVerifier<Integer>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public BigDecimalScaleVerifierImpl(BigDecimalPrecisionValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveNumberVerifier<Integer> getThis()
	{
		return this;
	}
}
