/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.PrimitiveFloatingPointValidator;
import com.github.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifier;

/**
 * Default implementation of {@code PrimitiveFloatingPointVerifier} for {@code float}s.
 */
public final class PrimitiveFloatVerifierImpl
	extends AbstractFloatingPointVerifier
	<PrimitiveFloatingPointVerifier<Float>, PrimitiveFloatingPointValidator<Float>, Float>
	implements PrimitiveFloatingPointVerifier<Float>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveFloatVerifierImpl(PrimitiveFloatingPointValidator<Float> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<Float> getThis()
	{
		return this;
	}
}
