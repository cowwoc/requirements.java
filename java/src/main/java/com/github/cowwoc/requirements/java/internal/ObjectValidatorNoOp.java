/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ObjectValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.List;

/**
 * An ObjectValidator that ignores subsequent validations due to an incompatible type conversion.
 *
 * @param <T> the type of the value being validated
 */
public final class ObjectValidatorNoOp<T> extends AbstractObjectValidatorNoOp<ObjectValidator<T>, T>
	implements ObjectValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	ObjectValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ObjectValidator<T> getThis()
	{
		return this;
	}
}
