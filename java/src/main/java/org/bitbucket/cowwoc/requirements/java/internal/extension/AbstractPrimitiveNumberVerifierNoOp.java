/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveNumberVerifier;

/**
 * An {@code ExtensiblePrimitiveNumberVerifier} that does nothing.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractPrimitiveNumberVerifierNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<S, T>
	implements ExtensiblePrimitiveNumberVerifier<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public AbstractPrimitiveNumberVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isNegative()
	{
		return super.isNegative();
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isNotNegative()
	{
		return super.isNotNegative();
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isZero()
	{
		return super.isZero();
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isNotZero()
	{
		return super.isNotZero();
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isPositive()
	{
		return super.isPositive();
	}

	@Deprecated
	@Override
	@SuppressWarnings("EmptyMethod")
	public S isNotPositive()
	{
		return super.isNotPositive();
	}
}
