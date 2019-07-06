/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveLongArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveLongArrayVerifier}.
 */
public final class PrimitiveLongArrayVerifierImpl
	extends AbstractArrayVerifier<PrimitiveLongArrayVerifier, PrimitiveLongArrayValidator, Long, long[]>
	implements PrimitiveLongArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveLongArrayVerifierImpl(PrimitiveLongArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveLongArrayVerifier getThis()
	{
		return this;
	}
}
