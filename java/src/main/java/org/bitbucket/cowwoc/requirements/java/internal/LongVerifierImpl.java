/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.IntegerValidator;
import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code IntegerVerifier<Long>}.
 */
public final class LongVerifierImpl
	extends AbstractNumberVerifier<IntegerVerifier<Long>, IntegerValidator<Long>, Long>
	implements IntegerVerifier<Long>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public LongVerifierImpl(IntegerValidator<Long> validator)
	{
		super(validator);
	}

	@Override
	protected IntegerVerifier<Long> getThis()
	{
		return this;
	}
}
