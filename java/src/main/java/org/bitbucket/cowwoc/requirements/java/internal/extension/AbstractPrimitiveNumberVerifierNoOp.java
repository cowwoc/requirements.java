/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * An {@code ExtensiblePrimitiveVerifier} that does nothing. A validator that ignores all subsequent
 * failures because they are guaranteed to fail and would add any value to the end-user. For example, an
 * attempt was made to dereference null or cast the value to an incompatible type.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value being verified
 */
public abstract class AbstractPrimitiveNumberVerifierNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<S, T>
	implements ExtensiblePrimitiveVerifier<S, T>
{
	@Override
	@Deprecated
	public S isNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNegative();
	}

	@Override
	@Deprecated
	public S isNotNegative()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotNegative();
	}

	@Override
	@Deprecated
	public S isZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isZero();
	}

	@Override
	@Deprecated
	public S isNotZero()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotZero();
	}

	@Override
	@Deprecated
	public S isPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isPositive();
	}

	@Override
	@Deprecated
	public S isNotPositive()
	{
		// Suppress warning about extending class with deprecated methods
		return super.isNotPositive();
	}
}
