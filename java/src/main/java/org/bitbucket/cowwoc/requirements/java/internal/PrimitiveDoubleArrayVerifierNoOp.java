/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveDoubleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveDoubleArrayVerifier} that does nothing.
 */
public final class PrimitiveDoubleArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveDoubleArrayVerifier, Double, double[]>
	implements PrimitiveDoubleArrayVerifier
{
	private static final PrimitiveDoubleArrayVerifierNoOp INSTANCE = new PrimitiveDoubleArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveDoubleArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveDoubleArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveDoubleArrayVerifier getThis()
	{
		return this;
	}
}
