/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code PrimitiveIntegerVerifier}.
 *
 * @param <T> the type of the value being verified
 */
public final class PrimitiveIntegerVerifierImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifier<PrimitiveIntegerVerifier<T>, PrimitiveIntegerValidator<T>, T>
	implements PrimitiveIntegerVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveIntegerVerifierImpl(PrimitiveIntegerValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveIntegerVerifier<T> getThis()
	{
		return this;
	}
}
