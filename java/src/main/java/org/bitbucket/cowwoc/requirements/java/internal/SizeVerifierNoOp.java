/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.SizeVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * A {@code SizeVerifier} that does nothing.
 */
public final class SizeVerifierNoOp
	extends AbstractPrimitiveNumberVerifierNoOp<SizeVerifier, Integer>
	implements SizeVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public SizeVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected SizeVerifier getThis()
	{
		return this;
	}
}
