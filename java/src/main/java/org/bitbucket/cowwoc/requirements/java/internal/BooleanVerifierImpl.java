/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BooleanValidator;
import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractBooleanVerifier;

/**
 * Default implementation of {@code BooleanVerifier}.
 */
public final class BooleanVerifierImpl
	extends AbstractBooleanVerifier<BooleanVerifier, BooleanValidator>
	implements BooleanVerifier
{
	/**
	 * @param validator the validator to delegate to
	 * @throws AssertionError if {@code validator} is null
	 */
	public BooleanVerifierImpl(BooleanValidator validator)
	{
		super(validator);
	}

	@Override
	protected BooleanVerifier getThis()
	{
		return this;
	}
}
