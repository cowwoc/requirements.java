/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.FloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifier;

/**
 * Default implementation of {@code FloatingPointVerifier<Float>}.
 */
public final class FloatVerifierImpl
	extends AbstractFloatingPointVerifier<FloatingPointVerifier<Float>, FloatingPointValidator<Float>, Float>
	implements FloatingPointVerifier<Float>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public FloatVerifierImpl(FloatingPointValidator<Float> validator)
	{
		super(validator);
	}

	@Override
	protected FloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
