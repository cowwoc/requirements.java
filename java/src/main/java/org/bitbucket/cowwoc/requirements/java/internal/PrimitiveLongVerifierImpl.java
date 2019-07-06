/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code PrimitiveInteger} for {@code long}s.
 */
public final class PrimitiveLongVerifierImpl
	extends AbstractNumberVerifier<PrimitiveIntegerVerifier<Long>, PrimitiveIntegerValidator<Long>, Long>
	implements PrimitiveIntegerVerifier<Long>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveLongVerifierImpl(PrimitiveIntegerValidator<Long> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveIntegerVerifier<Long> getThis()
	{
		return this;
	}
}
