/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveNumberValidator;
import com.github.cowwoc.requirements.java.PrimitiveNumberVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code PrimitiveNumberVerifier}.
 *
 * @param <T> the type of the value being verified
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

	@Override
	@Deprecated
	public PrimitiveNumberVerifier<T> isNull()
	{
		validator.isNull();
		return validationResult();
	}

	@Override
	@Deprecated
	public PrimitiveNumberVerifier<T> isNotNull()
	{
		validator.isNotNull();
		return validationResult();
	}
}
