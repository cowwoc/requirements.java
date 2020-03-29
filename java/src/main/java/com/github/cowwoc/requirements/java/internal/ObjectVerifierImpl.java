/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ObjectValidator;
import com.github.cowwoc.requirements.java.ObjectVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

/**
 * Default implementation of {@code ObjectVerifier}.
 *
 * @param <T> the type of the value being verified
 */
public final class ObjectVerifierImpl<T>
	extends AbstractObjectVerifier<ObjectVerifier<T>, ObjectValidator<T>, T>
	implements ObjectVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ObjectVerifierImpl(ObjectValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected ObjectVerifier<T> getThis()
	{
		return this;
	}
}
