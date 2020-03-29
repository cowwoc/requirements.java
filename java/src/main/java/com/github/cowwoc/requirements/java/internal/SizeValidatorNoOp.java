/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.SizeValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberValidatorNoOp;

import java.util.List;

/**
 * A SizeValidator that ignores subsequent validations due to an incompatible type conversion.
 */
public final class SizeValidatorNoOp
	extends AbstractPrimitiveNumberValidatorNoOp<SizeValidator, Integer>
	implements SizeValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public SizeValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected SizeValidator getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public SizeValidator isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}

	@Override
	@Deprecated
	public SizeValidator isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}
}
