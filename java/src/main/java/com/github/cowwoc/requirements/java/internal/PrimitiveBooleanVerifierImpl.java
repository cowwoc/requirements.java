/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveBooleanValidator;
import com.github.cowwoc.requirements.java.PrimitiveBooleanVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractBooleanVerifier;

/**
 * Default implementation of {@code PrimitiveBooleanVerifier}.
 */
public final class PrimitiveBooleanVerifierImpl
	extends AbstractBooleanVerifier<PrimitiveBooleanVerifier, PrimitiveBooleanValidator>
	implements PrimitiveBooleanVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveBooleanVerifierImpl(PrimitiveBooleanValidator validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveBooleanVerifier getThis()
	{
		return this;
	}

	@Override
	@Deprecated
	public PrimitiveBooleanVerifier isNotNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Override
	@Deprecated
	public PrimitiveBooleanVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
