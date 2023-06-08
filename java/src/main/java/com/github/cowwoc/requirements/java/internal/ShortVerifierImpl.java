/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.IntegerValidator;
import com.github.cowwoc.requirements.java.IntegerVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code IntegerVerifier<Short>}.
 */
public final class ShortVerifierImpl
	extends AbstractNumberVerifier<IntegerVerifier<Short>, IntegerValidator<Short>, Short>
	implements IntegerVerifier<Short>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public ShortVerifierImpl(IntegerValidator<Short> validator)
	{
		super(validator);
	}

	@Override
	protected IntegerVerifier<Short> getThis()
	{
		return this;
	}
}