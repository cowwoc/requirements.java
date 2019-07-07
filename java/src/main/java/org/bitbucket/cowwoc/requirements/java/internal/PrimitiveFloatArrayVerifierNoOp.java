/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveFloatArrayVerifier} that does nothing.
 */
public final class PrimitiveFloatArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveFloatArrayVerifier, Float, float[]>
	implements PrimitiveFloatArrayVerifier
{
	private static final PrimitiveFloatArrayVerifierNoOp INSTANCE = new PrimitiveFloatArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveFloatArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveFloatArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveFloatArrayVerifier getThis()
	{
		return this;
	}
}
