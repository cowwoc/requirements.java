/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveShortArrayVerifier} that does nothing.
 */
public final class PrimitiveShortArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveShortArrayVerifier, Short, short[]>
	implements PrimitiveShortArrayVerifier
{
	private static final PrimitiveShortArrayVerifierNoOp INSTANCE = new PrimitiveShortArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveShortArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveShortArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveShortArrayVerifier getThis()
	{
		return this;
	}
}
