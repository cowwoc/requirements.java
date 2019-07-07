/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

import java.util.Optional;

/**
 * An {@code OptionalVerifier} that does nothing.
 */
public final class OptionalVerifierNoOp
	extends AbstractObjectVerifierNoOp<OptionalVerifier, Optional<?>>
	implements OptionalVerifier
{
	private static final OptionalVerifierNoOp INSTANCE = new OptionalVerifierNoOp();

	/**
	 * @return the singleton instance
	 */
	public static OptionalVerifierNoOp getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Prevent construction.
	 */
	private OptionalVerifierNoOp()
	{
	}

	@Override
	protected OptionalVerifier getThis()
	{
		return this;
	}

	@Override
	public OptionalVerifier isPresent()
	{
		return this;
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		return this;
	}

	@Override
	public OptionalVerifier contains(Object value, String name)
	{
		return this;
	}
}
