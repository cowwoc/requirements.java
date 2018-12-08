/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;

/**
 * An implementation of {@link PrimitiveBooleanVerifier} that does nothing.
 */
public final class NoOpPrimitiveBooleanVerifier
	extends NoOpComparableCapabilities<PrimitiveBooleanVerifier, Boolean>
	implements PrimitiveBooleanVerifier
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveBooleanVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveBooleanVerifier getThis()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanVerifier isTrue()
	{
		return this;
	}

	@Override
	public PrimitiveBooleanVerifier isFalse()
	{
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveBooleanVerifier isNotNull()
	{
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveBooleanVerifier isNull()
	{
		return super.isNull();
	}
}
