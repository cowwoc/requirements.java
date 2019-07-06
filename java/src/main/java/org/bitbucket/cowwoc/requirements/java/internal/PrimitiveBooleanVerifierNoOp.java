/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * An implementation of {@code PrimitiveBooleanVerifier} that does nothing.
 */
public final class PrimitiveBooleanVerifierNoOp
	extends AbstractComparableVerifierNoOp<PrimitiveBooleanVerifier, Boolean>
	implements PrimitiveBooleanVerifier
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveBooleanVerifierNoOp(Configuration config)
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
		// Suppress warning about extending class with deprecated methods
		return super.isNotNull();
	}

	@Deprecated
	@Override
	public PrimitiveBooleanVerifier isNull()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNull();
	}
}
