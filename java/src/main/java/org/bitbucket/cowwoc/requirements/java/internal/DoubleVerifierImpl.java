/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.FloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifier;

/**
 * Default implementation of {@code FloatingPointVerifier<Double>}.
 */
public final class DoubleVerifierImpl
	extends AbstractFloatingPointVerifier<FloatingPointVerifier<Double>, FloatingPointValidator<Double>, Double>
	implements FloatingPointVerifier<Double>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public DoubleVerifierImpl(FloatingPointValidator<Double> validator)
	{
		super(validator);
	}

	@Override
	protected FloatingPointVerifier<Double> getThis()
	{
		return this;
	}
}
