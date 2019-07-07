/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.IntegerValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberValidatorNoOp;

import java.util.List;

/**
 * An {@code IntegerValidator} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class IntegerValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidatorNoOp<IntegerValidator<T>, T>
	implements IntegerValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public IntegerValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected IntegerValidator<T> getThis()
	{
		return this;
	}
}
