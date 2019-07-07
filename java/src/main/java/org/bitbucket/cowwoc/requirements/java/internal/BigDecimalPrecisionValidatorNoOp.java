/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberValidatorNoOp;

import java.util.List;

/**
 * A {@code BigDecimalPrecisionValidator} that does nothing.
 */
public final class BigDecimalPrecisionValidatorNoOp
	extends AbstractPrimitiveNumberValidatorNoOp<BigDecimalPrecisionValidator, Integer>
	implements BigDecimalPrecisionValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public BigDecimalPrecisionValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected BigDecimalPrecisionValidator getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotZero();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isPositive();
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionValidator isNotPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotPositive();
	}
}
