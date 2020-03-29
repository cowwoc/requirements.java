/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.NumberValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidatorNoOp;

import java.util.List;

/**
 * A {@code NumberValidator} that does nothing.
 *
 * @param <T> the type of the value being validated
 */
public final class NumberValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidatorNoOp<NumberValidator<T>, T>
	implements NumberValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public NumberValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected NumberValidatorNoOp<T> getThis()
	{
		return this;
	}
}
