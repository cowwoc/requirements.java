/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code ArrayVerifier}.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class ArrayVerifierImpl<E, A>
	extends AbstractArrayVerifier<ArrayVerifier<E, A>, ArrayValidator<E, A>, E, A>
	implements ArrayVerifier<E, A>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ArrayVerifierImpl(ArrayValidator<E, A> validator)
	{
		super(validator);
	}

	@Override
	protected ArrayVerifier<E, A> getThis()
	{
		return this;
	}
}
