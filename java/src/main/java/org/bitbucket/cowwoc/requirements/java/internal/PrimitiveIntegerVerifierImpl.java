/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifier;

/**
 * Default implementation of {@code PrimitiveIntegerVerifier} for {@code int}s.
 */
public final class PrimitiveIntegerVerifierImpl
	extends AbstractNumberVerifier
	<PrimitiveIntegerVerifier<Integer>, PrimitiveIntegerValidator<Integer>, Integer>
	implements PrimitiveIntegerVerifier<Integer>,
	ExtensibleIntegerVerifier<PrimitiveIntegerVerifier<Integer>, Integer>
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public PrimitiveIntegerVerifierImpl(PrimitiveIntegerValidator<Integer> validator)
	{
		super(validator);
	}

	@Override
	protected PrimitiveIntegerVerifier<Integer> getThis()
	{
		return this;
	}
}
