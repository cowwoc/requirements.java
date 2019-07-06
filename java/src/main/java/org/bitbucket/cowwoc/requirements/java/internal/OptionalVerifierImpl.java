/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.OptionalValidator;
import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Optional;

/**
 * Default implementation of {@code OptionalVerifier}.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalVerifierImpl
	extends AbstractObjectVerifier<OptionalVerifier, OptionalValidator, Optional<?>>
	implements OptionalVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public OptionalVerifierImpl(OptionalValidator validator)
	{
		super(validator);
	}

	@Override
	protected OptionalVerifier getThis()
	{
		return this;
	}

	@Override
	public OptionalVerifier isPresent()
	{
		validator.isPresent();
		return validationResult();
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		validator.isEmpty();
		return validationResult();
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		validator.contains(value);
		return validationResult();
	}

	@Override
	public OptionalVerifier contains(Object expected, String name)
	{
		validator.contains(expected, name);
		return validationResult();
	}
}
