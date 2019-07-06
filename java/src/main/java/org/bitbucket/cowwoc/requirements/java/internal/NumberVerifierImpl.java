/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.NumberValidator;
import org.bitbucket.cowwoc.requirements.java.NumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code NumberVerifier}.
 *
 * @param <T> the type of the value
 */
public final class NumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifier<NumberVerifier<T>, NumberValidator<T>, T>
	implements NumberVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public NumberVerifierImpl(NumberValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected NumberVerifier<T> getThis()
	{
		return this;
	}
}
