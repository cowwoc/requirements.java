/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code ExtensibleArrayVerifier}.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class PrimitiveArrayVerifierImpl<E, A>
	extends AbstractArrayVerifier<PrimitiveArrayVerifier<E, A>, PrimitiveArrayValidator<E, A>, E, A>
	implements PrimitiveArrayVerifier<E, A>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveArrayVerifierImpl(PrimitiveArrayValidator<E, A> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveArrayVerifier<E, A> getThis()
	{
		return this;
	}
}
