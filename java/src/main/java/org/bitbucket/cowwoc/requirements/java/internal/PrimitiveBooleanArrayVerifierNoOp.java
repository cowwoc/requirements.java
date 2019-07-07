/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveBooleanArrayVerifier} that does nothing.
 */
public final class PrimitiveBooleanArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveBooleanArrayVerifier, Boolean, boolean[]>
	implements PrimitiveBooleanArrayVerifier
{
	private static final PrimitiveBooleanArrayVerifierNoOp INSTANCE = new PrimitiveBooleanArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveBooleanArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveBooleanArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveBooleanArrayVerifier getThis()
	{
		return this;
	}
}
