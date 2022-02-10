/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveIntegerValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberValidatorNoOp;

import java.util.List;

/**
 * A {@code PrimitiveIntegerValidator} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class PrimitiveIntegerValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberValidatorNoOp<PrimitiveIntegerValidator<T>, T>
	implements PrimitiveIntegerValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	PrimitiveIntegerValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PrimitiveIntegerValidator<T> getThis()
	{
		return this;
	}
}
