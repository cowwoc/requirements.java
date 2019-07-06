/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveLongArrayVerifier} that does nothing.
 */
public final class PrimitiveLongArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveLongArrayVerifier, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveLongArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveLongArrayVerifier getThis()
	{
		return this;
	}
}
