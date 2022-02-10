/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.FloatingPointValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractFloatingPointValidatorNoOp;

import java.util.List;

/**
 * A {@code FloatingPointValidator} that does nothing.
 *
 * @param <T> the type of the floating-point number
 */
public final class FloatingPointValidatorNoOp<T extends Number & Comparable<? super T>>
	extends AbstractFloatingPointValidatorNoOp<FloatingPointValidator<T>, T>
	implements FloatingPointValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	FloatingPointValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected FloatingPointValidator<T> getThis()
	{
		return this;
	}
}
