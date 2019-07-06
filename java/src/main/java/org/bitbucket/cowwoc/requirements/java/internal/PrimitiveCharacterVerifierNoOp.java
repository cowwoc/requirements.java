/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * A {@code PrimitiveCharacterVerifier} that does nothing.
 */
public final class PrimitiveCharacterVerifierNoOp
	extends AbstractComparableVerifierNoOp<PrimitiveCharacterVerifier, Character>
	implements PrimitiveCharacterVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveCharacterVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveCharacterVerifier getThis()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveCharacterVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
