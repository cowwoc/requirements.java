/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ComparableValidator;
import com.github.cowwoc.requirements.java.ValidationFailure;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableValidatorNoOp;

import java.util.List;

/**
 * A {@code ComparableValidator} that does nothing.
 *
 * @param <T> the type of the value being validated
 */
public final class ComparableValidatorNoOp<T extends Comparable<? super T>>
	extends AbstractComparableValidatorNoOp<ComparableValidator<T>, T>
	implements ComparableValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public ComparableValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ComparableValidator<T> getThis()
	{
		return this;
	}
}
