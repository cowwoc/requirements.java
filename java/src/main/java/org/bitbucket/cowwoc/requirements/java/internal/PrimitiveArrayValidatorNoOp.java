/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ValidationFailure;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayValidatorNoOp;

import java.util.List;

/**
 * An {@code PrimitiveArrayValidator} that does nothing.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class PrimitiveArrayValidatorNoOp<E, A>
	extends AbstractArrayValidatorNoOp<PrimitiveArrayValidator<E, A>, E, A>
	implements PrimitiveArrayValidator<E, A>
{
	/**
	 * @param failures the list of validation failures
	 * @throws AssertionError if {@code failures} is null
	 */
	public PrimitiveArrayValidatorNoOp(List<ValidationFailure> failures)
	{
		super(failures);
	}

	@Override
	protected PrimitiveArrayValidatorNoOp<E, A> getThis()
	{
		return this;
	}
}