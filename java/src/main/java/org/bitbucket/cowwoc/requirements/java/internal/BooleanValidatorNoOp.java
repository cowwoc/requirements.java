/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BooleanValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;

import java.util.List;

/**
 * A {@code BooleanValidator} that does nothing.
 */
public final class BooleanValidatorNoOp
	extends AbstractComparableValidatorNoOp<BooleanValidator, Boolean>
	implements BooleanValidator
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public BooleanValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected BooleanValidator getThis()
	{
		return this;
	}

	@Override
	public BooleanValidator isTrue()
	{
		return this;
	}

	@Override
	public BooleanValidator isFalse()
	{
		return this;
	}
}
