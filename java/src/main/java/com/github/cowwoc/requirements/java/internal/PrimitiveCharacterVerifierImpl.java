/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveCharacterVerifier;
import com.github.cowwoc.requirements.java.PrimitiveCharacterValidator;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableVerifier;

/**
 * Default implementation of {@code PrimitiveCharacterVerifier}.
 */
public final class PrimitiveCharacterVerifierImpl
	extends AbstractComparableVerifier<PrimitiveCharacterVerifier, PrimitiveCharacterValidator, Character>
	implements PrimitiveCharacterVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveCharacterVerifierImpl(PrimitiveCharacterValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveCharacterVerifier getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Override
	@Deprecated
	public PrimitiveCharacterVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
