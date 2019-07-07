/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveNumberVerifier;

/**
 * An {@code ExtensiblePrimitiveNumberVerifier} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public abstract class AbstractPrimitiveNumberVerifierNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<S, T>
	implements ExtensiblePrimitiveNumberVerifier<S, T>
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public AbstractPrimitiveNumberVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Deprecated
	@Override
	public S isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}

	@Deprecated
	@Override
	public S isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}

	@Deprecated
	@Override
	public S isZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isZero();
	}

	@Deprecated
	@Override
	public S isNotZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotZero();
	}

	@Deprecated
	@Override
	public S isPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isPositive();
	}

	@Deprecated
	@Override
	public S isNotPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotPositive();
	}
}
