/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;

/**
 * An implementation of {@link BooleanVerifier} that does nothing.
 */
public final class NoOpBooleanVerifier
	extends NoOpComparableCapabilities<BooleanVerifier, Boolean>
	implements BooleanVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBooleanVerifier(Configuration config)
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
