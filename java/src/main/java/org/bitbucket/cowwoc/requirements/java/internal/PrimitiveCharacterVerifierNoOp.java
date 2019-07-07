/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * A {@code PrimitiveCharacterVerifier} that does nothing.
 */
public final class PrimitiveCharacterVerifierNoOp
	extends AbstractComparableVerifierNoOp<PrimitiveCharacterVerifier, Character>
	implements PrimitiveCharacterVerifier
{
	private static final PrimitiveCharacterVerifierNoOp INSTANCE = new PrimitiveCharacterVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveCharacterVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveCharacterVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveCharacterVerifier getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
