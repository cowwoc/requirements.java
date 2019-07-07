/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;

import java.util.List;

/**
 * An {@code ExtensibleFloatingPointValidator} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of validator returned by the methods
 * @param <T> the type of the value being validated
 */
public abstract class AbstractFloatingPointValidatorNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberValidatorNoOp<S, T>
	implements ExtensibleFloatingPointValidator<S, T>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public AbstractFloatingPointValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	public S isNumber()
	{
		return getThis();
	}

	@Override
	public S isNotNumber()
	{
		return getThis();
	}

	@Override
	public S isFinite()
	{
		return getThis();
	}

	@Override
	public S isNotFinite()
	{
		return getThis();
	}
}
