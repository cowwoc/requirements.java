/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.BooleanVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * A {@code BooleanVerifier} that does nothing.
 */
public final class BooleanVerifierNoOp
	extends AbstractComparableVerifierNoOp<BooleanVerifier, Boolean>
	implements BooleanVerifier
{
	private static final BooleanVerifierNoOp INSTANCE = new BooleanVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static BooleanVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private BooleanVerifierNoOp()
	{
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
