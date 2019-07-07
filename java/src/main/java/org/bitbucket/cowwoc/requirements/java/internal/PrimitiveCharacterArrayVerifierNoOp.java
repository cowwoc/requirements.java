/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveCharacterArrayVerifier} that does nothing.
 */
public final class PrimitiveCharacterArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveCharacterArrayVerifier, Character, char[]>
	implements PrimitiveCharacterArrayVerifier
{
	private static final PrimitiveCharacterArrayVerifierNoOp INSTANCE =
		new PrimitiveCharacterArrayVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveCharacterArrayVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveCharacterArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveCharacterArrayVerifier getThis()
	{
		return this;
	}
}
