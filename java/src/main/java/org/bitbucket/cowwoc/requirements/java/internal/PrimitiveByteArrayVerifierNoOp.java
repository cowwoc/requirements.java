/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code PrimitiveByteArrayVerifier} that does nothing.
 */
public final class PrimitiveByteArrayVerifierNoOp
	extends AbstractArrayVerifierNoOp<PrimitiveByteArrayVerifier, Byte, byte[]>
	implements PrimitiveByteArrayVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveByteArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveByteArrayVerifier getThis()
	{
		return this;
	}
}
