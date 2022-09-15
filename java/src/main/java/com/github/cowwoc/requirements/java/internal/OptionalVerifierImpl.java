/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.OptionalValidator;
import com.github.cowwoc.requirements.java.OptionalVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifier;

import java.util.Optional;

/**
 * Default implementation of {@code OptionalVerifier}.
 */
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
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		validator.isEmpty();
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		validator.contains(value);
		return validationResult(IllegalArgumentException.class);
	}

	@Override
	public OptionalVerifier contains(Object expected, String name)
	{
		validator.contains(expected, name);
		return validationResult(IllegalArgumentException.class);
	}
}
