/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveCharacterArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifier;

/**
 * Default implementation of {@code PrimitiveCharacterArrayVerifier}.
 */
public final class PrimitiveCharacterArrayVerifierImpl
	extends AbstractArrayVerifier
	<PrimitiveCharacterArrayVerifier, PrimitiveCharacterArrayValidator, Character, char[]>
	implements PrimitiveCharacterArrayVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveCharacterArrayVerifierImpl(PrimitiveCharacterArrayValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveCharacterArrayVerifier getThis()
	{
		return this;
	}
}
