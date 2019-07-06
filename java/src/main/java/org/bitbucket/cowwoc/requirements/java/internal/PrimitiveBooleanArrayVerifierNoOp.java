/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveBooleanArrayVerifier} that does nothing.
 */
public final class PrimitiveBooleanArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveBooleanArrayVerifier, Boolean, boolean[]>
	implements PrimitiveBooleanArrayVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveBooleanArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveBooleanArrayVerifier getThis()
	{
		return this;
	}
}
