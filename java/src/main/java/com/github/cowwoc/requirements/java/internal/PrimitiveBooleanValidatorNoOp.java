/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;

import java.util.List;

/**
 * A {@code PrimitiveBooleanValidator} that does nothing.
 */
public final class PrimitiveBooleanValidatorNoOp
	extends AbstractComparableValidatorNoOp<PrimitiveBooleanValidator, Boolean>
	implements PrimitiveBooleanValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	PrimitiveBooleanValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PrimitiveBooleanValidator getThis()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isTrue()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanValidator isFalse()
	{
		return this;
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Override
	@Deprecated
	public PrimitiveBooleanValidator isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
