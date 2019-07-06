/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveDoubleArrayVerifier}.
 */
public final class PrimitiveDoubleArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveDoubleArrayVerifier, PrimitiveDoubleArrayValidator, Double, double[]>
	implements PrimitiveDoubleArrayVerifier
{
	@Override
	protected PrimitiveDoubleArrayVerifier getThis()
	{
		return this;
	}

	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveDoubleArrayVerifierImpl(PrimitiveDoubleArrayValidator validator)
	{
		super(validator);
	}
}
