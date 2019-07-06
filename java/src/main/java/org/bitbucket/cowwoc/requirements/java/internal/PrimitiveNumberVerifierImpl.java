/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code PrimitiveNumberVerifier}.
 *
 * @param <T> the type of the value
 */
public final class PrimitiveNumberVerifierImpl<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifier<PrimitiveNumberVerifier<T>, PrimitiveNumberValidator<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveNumberVerifierImpl(PrimitiveNumberValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveNumberVerifier<T> getThis()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<T> isNull()
	{
		validator.isNull();
		return validationResult();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<T> isNotNull()
	{
		validator.isNotNull();
		return validationResult();
	}
}
