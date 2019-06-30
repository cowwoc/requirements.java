/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveShortArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveShortArrayVerifier} that does nothing.
 */
public final class PrimitiveShortArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveShortArrayVerifier, Short, short[]>
	implements PrimitiveShortArrayVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveShortArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveShortArrayVerifier getThis()
	{
		return this;
	}
}
