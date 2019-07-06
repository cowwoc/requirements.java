/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveIntegerArrayVerifier}.
 */
public final class PrimitiveIntegerArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveIntegerArrayVerifier, PrimitiveIntegerArrayValidator, Integer, int[]>
	implements PrimitiveIntegerArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveIntegerArrayVerifierImpl(PrimitiveIntegerArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveIntegerArrayVerifier getThis()
	{
		return this;
	}
}
