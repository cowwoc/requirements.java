/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.extension;

import com.github.cowwoc.requirements.java.extension.ExtensibleBooleanValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleBooleanVerifier;

/**
 * An implementation of {@code ExtensibleBooleanVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 */
public abstract class AbstractBooleanVerifier<S, V extends ExtensibleBooleanValidator<V>>
	extends AbstractObjectVerifier<S, V, Boolean>
	implements ExtensibleBooleanVerifier<S>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractBooleanVerifier(V validator)
	{
		super(validator);
	}

	@Override
	public S isTrue()
	{
		validator.isTrue();
		return validationResult();
	}

	@Override
	public S isFalse()
	{
		validator.isFalse();
		return validationResult();
	}
}
