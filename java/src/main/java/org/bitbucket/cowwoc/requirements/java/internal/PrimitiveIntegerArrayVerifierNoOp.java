/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveIntegerArrayVerifier} that does nothing.
 */
public final class PrimitiveIntegerArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveIntegerArrayVerifier, Integer, int[]>
	implements PrimitiveIntegerArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveIntegerArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveIntegerArrayVerifier getThis()
	{
		return this;
	}
}
