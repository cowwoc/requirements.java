/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveLongArrayVerifier} that does nothing.
 */
public final class PrimitiveLongArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveLongArrayVerifier, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	private static final PrimitiveLongArrayVerifierNoOp INSTANCE = new PrimitiveLongArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveLongArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveLongArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveLongArrayVerifier getThis()
	{
		return this;
	}
}
