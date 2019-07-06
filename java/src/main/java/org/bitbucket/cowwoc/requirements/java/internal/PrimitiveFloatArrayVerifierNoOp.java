/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveFloatArrayVerifier} that does nothing.
 */
public final class PrimitiveFloatArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveFloatArrayVerifier, Float, float[]>
	implements PrimitiveFloatArrayVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveFloatArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveFloatArrayVerifier getThis()
	{
		return this;
	}
}
