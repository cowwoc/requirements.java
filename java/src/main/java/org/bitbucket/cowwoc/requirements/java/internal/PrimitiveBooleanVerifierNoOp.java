/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveBooleanVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * A {@code PrimitiveBooleanVerifier} that does nothing.
 */
public final class PrimitiveBooleanVerifierNoOp
	extends AbstractComparableVerifierNoOp<PrimitiveBooleanVerifier, Boolean>
	implements PrimitiveBooleanVerifier
{
	private static final PrimitiveBooleanVerifierNoOp INSTANCE = new PrimitiveBooleanVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static PrimitiveBooleanVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveBooleanVerifierNoOp()
	{
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
