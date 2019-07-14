/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidatorNoOp;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * A {@code BigDecimalValidator} that does nothing.
 */
public final class BigDecimalValidatorNoOp
	extends AbstractNumberValidatorNoOp<BigDecimalValidator, BigDecimal>
	implements BigDecimalValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public BigDecimalValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected BigDecimalValidator getThis()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionValidator precision()
	{
		return new BigDecimalPrecisionValidatorNoOp(getFailures());
	}

	@Override
	public BigDecimalValidator precision(Consumer<BigDecimalPrecisionValidator> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}

	@Override
	public PrimitiveNumberValidator<Integer> scale()
	{
		return new PrimitiveNumberValidatorNoOp<>(getFailures());
	}

	@Override
	public BigDecimalValidator scale(Consumer<PrimitiveNumberValidator<Integer>> consumer)
	{
		if (consumer == null)
			throw new NullPointerException("consumer may not be null");
		return this;
	}
}
