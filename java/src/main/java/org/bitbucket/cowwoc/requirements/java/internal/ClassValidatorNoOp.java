/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectValidatorNoOp;

import java.util.List;

/**
 * A {@code ClassValidator} that does nothing.
 *
 * @param <T> the type of the class
 */
public final class ClassValidatorNoOp<T>
	extends AbstractObjectValidatorNoOp<ClassValidator<T>, Class<T>>
	implements ClassValidator<T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public ClassValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected ClassValidator<T> getThis()
	{
		return this;
	}

	@Override
	public ClassValidator<T> isSupertypeOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ClassValidator<T> isSubtypeOf(Class<?> type)
	{
		return this;
	}
}
