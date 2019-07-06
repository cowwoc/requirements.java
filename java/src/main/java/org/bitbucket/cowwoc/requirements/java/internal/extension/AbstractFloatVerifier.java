/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointVerifier;

/**
 * Default implementation of {@code ExtensibleFloatingPointVerifier} for {@code float}s.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <V> the type of validator used by the verifier
 */
public abstract class AbstractFloatVerifier<S, V extends ExtensibleFloatingPointValidator<V, Float>>
	extends AbstractNumberVerifier<S, V, Float>
	implements ExtensibleFloatingPointVerifier<S, Float>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	protected AbstractFloatVerifier(V validator)
	{
		super(validator);
	}

	@Override
	public S isNumber()
	{
		validator = validator.isNumber();
		return validationResult();
	}

	@Override
	public S isNotNumber()
	{
		validator = validator.isNotNumber();
		return validationResult();
	}

	@Override
	public S isFinite()
	{
		validator = validator.isFinite();
		return validationResult();
	}

	@Override
	public S isNotFinite()
	{
		validator = validator.isNotFinite();
		return validationResult();
	}
}
