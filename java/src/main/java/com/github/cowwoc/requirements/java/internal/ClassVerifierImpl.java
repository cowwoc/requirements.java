/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ClassValidator;
import com.github.cowwoc.requirements.java.ClassVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

/**
 * Default implementation of {@code ClassVerifier}.
 *
 * @param <T> the type of the class
 */
public final class ClassVerifierImpl<T>
	extends AbstractObjectVerifier<ClassVerifier<T>, ClassValidator<T>, Class<T>>
	implements ClassVerifier<T>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ClassVerifierImpl(ClassValidator<T> validator)
	{
		super(validator);
	}

	@Override
	protected ClassVerifier<T> getThis()
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		validator = validator.isSupertypeOf(type);
		return validationResult();
	}

	@Override
	public ClassVerifier<T> isSubtypeOf(Class<?> type)
	{
		validator = validator.isSubtypeOf(type);
		return validationResult();
	}
}
