/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveCharacterArrayVerifier} that does nothing.
 */
public final class PrimitiveCharacterArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveCharacterArrayVerifier, Character, char[]>
	implements PrimitiveCharacterArrayVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveCharacterArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveCharacterArrayVerifier getThis()
	{
		return this;
	}
}
