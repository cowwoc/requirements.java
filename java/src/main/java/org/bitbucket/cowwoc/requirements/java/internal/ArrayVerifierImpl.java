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
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierImpl<A, E>
	extends AbstractArrayVerifier<ArrayVerifier<A, E>, ArrayValidator<A, E>, A, E>
	implements ArrayVerifier<A, E>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ArrayVerifierImpl(ArrayValidator<A, E> validator)
	{
		super(validator);
	}

	@Override
	protected ArrayVerifier<A, E> getThis()
	{
		return this;
	}
}
