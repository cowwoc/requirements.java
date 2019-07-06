/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveBooleanArrayVerifier}.
 */
public final class PrimitiveBooleanArrayVerifierImpl
	extends AbstractArrayVerifier
	<PrimitiveBooleanArrayVerifier, PrimitiveBooleanArrayValidator, Boolean, boolean[]>
	implements PrimitiveBooleanArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveBooleanArrayVerifierImpl(PrimitiveBooleanArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveBooleanArrayVerifier getThis()
	{
		return this;
	}
}
