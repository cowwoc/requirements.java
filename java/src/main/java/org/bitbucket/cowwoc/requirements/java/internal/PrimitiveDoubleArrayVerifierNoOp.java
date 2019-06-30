/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveDoubleArrayVerifier} that does nothing.
 */
public final class PrimitiveDoubleArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveDoubleArrayVerifier, Double, double[]>
	implements PrimitiveDoubleArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveDoubleArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveDoubleArrayVerifier getThis()
	{
		return this;
	}
}
