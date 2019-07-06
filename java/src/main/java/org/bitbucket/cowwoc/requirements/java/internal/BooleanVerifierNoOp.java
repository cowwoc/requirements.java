/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * An implementation of {@code BooleanVerifier} that does nothing.
 */
public final class BooleanVerifierNoOp
	extends AbstractComparableVerifierNoOp<BooleanVerifier, Boolean>
	implements BooleanVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public BooleanVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected BooleanVerifier getThis()
	{
		return this;
	}

	@Override
	public BooleanVerifier isTrue()
	{
		return this;
	}

	@Override
	public BooleanVerifier isFalse()
	{
		return this;
	}
}
