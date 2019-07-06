/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatVerifier;

/**
 * Default implementation of {@code PrimitiveFloatingPointVerifier} for {@code float}s.
 */
public final class PrimitiveFloatVerifierImpl
	extends AbstractFloatVerifier<PrimitiveFloatingPointVerifier<Float>, PrimitiveFloatingPointValidator<Float>>
	implements PrimitiveFloatingPointVerifier<Float>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveFloatVerifierImpl(PrimitiveFloatingPointValidator<Float> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
