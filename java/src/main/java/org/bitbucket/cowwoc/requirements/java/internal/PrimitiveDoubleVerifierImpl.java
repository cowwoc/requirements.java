/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifier;

/**
 * Default implementation of {@code PrimitiveFloatingPointVerifier} for {@code double}s.
 */
public final class PrimitiveDoubleVerifierImpl
	extends AbstractFloatingPointVerifier
	<PrimitiveFloatingPointVerifier<Double>, PrimitiveFloatingPointValidator<Double>, Double>
	implements PrimitiveFloatingPointVerifier<Double>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveDoubleVerifierImpl(PrimitiveFloatingPointValidator<Double> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Double> getThis()
	{
		return this;
	}
}
