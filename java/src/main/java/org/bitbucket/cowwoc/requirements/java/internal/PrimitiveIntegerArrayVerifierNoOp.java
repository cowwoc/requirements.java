/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveIntegerArrayVerifier} that does nothing.
 */
public final class PrimitiveIntegerArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveIntegerArrayVerifier, Integer, int[]>
	implements PrimitiveIntegerArrayVerifier
{
	private static final PrimitiveIntegerArrayVerifierNoOp INSTANCE = new PrimitiveIntegerArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveIntegerArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveIntegerArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveIntegerArrayVerifier getThis()
	{
		return this;
	}
}
