/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveByteArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveByteArrayVerifier}.
 */
public final class PrimitiveByteArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveByteArrayVerifier, PrimitiveByteArrayValidator, Byte, byte[]>
	implements PrimitiveByteArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveByteArrayVerifierImpl(PrimitiveByteArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveByteArrayVerifier getThis()
	{
		return this;
	}
}
