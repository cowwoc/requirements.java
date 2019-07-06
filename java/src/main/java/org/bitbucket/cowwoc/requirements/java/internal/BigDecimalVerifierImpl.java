/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Default implementation of {@code BigDecimalVerifier}.
 */
public final class BigDecimalVerifierImpl
	extends AbstractNumberVerifier<BigDecimalVerifier, BigDecimalValidator, BigDecimal>
	implements BigDecimalVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public BigDecimalVerifierImpl(BigDecimalValidator validator)
	{
		super(validator);
	}

	@Override
	protected BigDecimalVerifier getThis()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		BigDecimalPrecisionValidator newValidator = validator.precision();
		return validationResult(() -> new BigDecimalPrecisionVerifierImpl(newValidator));
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(precision());
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> scale()
	{
		PrimitiveNumberValidator<Integer> newValidator = validator.scale();
		return validationResult(() -> new PrimitiveNumberVerifierImpl<>(newValidator));
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		consumer.accept(scale());
		return this;
	}
}
