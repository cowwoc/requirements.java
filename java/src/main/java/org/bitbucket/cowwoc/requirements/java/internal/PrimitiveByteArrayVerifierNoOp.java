/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveByteArrayVerifier} that does nothing.
 */
public final class PrimitiveByteArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveByteArrayVerifier, Byte, byte[]>
	implements PrimitiveByteArrayVerifier
{
	private static final PrimitiveByteArrayVerifierNoOp INSTANCE = new PrimitiveByteArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveByteArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveByteArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveByteArrayVerifier getThis()
	{
		return this;
	}
}
