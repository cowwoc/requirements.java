/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveNumberValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberValidatorNoOp;

import java.util.List;

/**
 * A PrimitiveNumberValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <T> the type of the integer number
 */
public final class PrimitiveNumberValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractPrimitiveNumberValidatorNoOp<PrimitiveNumberValidator<T>, T>
	implements PrimitiveNumberValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public PrimitiveNumberValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PrimitiveNumberValidator<T> getThis()
	{
		return this;
	}
}
