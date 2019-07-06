/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveFloatArrayVerifier}.
 */
public final class PrimitiveFloatArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveFloatArrayVerifier, PrimitiveFloatArrayValidator, Float, float[]>
	implements PrimitiveFloatArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveFloatArrayVerifierImpl(PrimitiveFloatArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveFloatArrayVerifier getThis()
	{
		return this;
	}
}
