/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.IntegerVerifier;
import com.github.cowwoc.requirements.java.IntegerValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code IntegerVerifier<Integer>}.
 */
public final class IntegerVerifierImpl
	extends AbstractNumberVerifier<IntegerVerifier<Integer>, IntegerValidator<Integer>, Integer>
	implements IntegerVerifier<Integer>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public IntegerVerifierImpl(IntegerValidator<Integer> validator)
	{
		super(validator);
	}

	@Override
	protected IntegerVerifier<Integer> getThis()
	{
		return this;
	}
}
