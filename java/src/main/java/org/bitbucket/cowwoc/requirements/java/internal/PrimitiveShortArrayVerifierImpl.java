/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveShortArrayVerifier}.
 */
public final class PrimitiveShortArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveShortArrayVerifier, PrimitiveShortArrayValidator, Short, short[]>
	implements PrimitiveShortArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveShortArrayVerifierImpl(PrimitiveShortArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveShortArrayVerifier getThis()
	{
		return this;
	}
}
