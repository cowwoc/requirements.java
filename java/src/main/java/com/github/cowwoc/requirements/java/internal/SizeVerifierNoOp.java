/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.SizeVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * A {@code SizeVerifier} that does nothing.
 */
public final class SizeVerifierNoOp
	extends AbstractPrimitiveNumberVerifierNoOp<SizeVerifier, Integer>
	implements SizeVerifier
{
	private static final SizeVerifierNoOp INSTANCE = new SizeVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static SizeVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private SizeVerifierNoOp()
	{
	}

	@Override
	protected SizeVerifier getThis()
	{
		return this;
	}
}
