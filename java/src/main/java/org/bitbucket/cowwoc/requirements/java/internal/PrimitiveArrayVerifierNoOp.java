/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * A {@code PrimitiveArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class PrimitiveArrayVerifierNoOp<E, A>
	extends AbstractArrayVerifierNoOp<PrimitiveArrayVerifier<E, A>, E, A>
	implements PrimitiveArrayVerifier<E, A>
{
	private static final PrimitiveArrayVerifierNoOp<?, ?> INSTANCE = new PrimitiveArrayVerifierNoOp<>();

	/**
	 * @param <E> the type of elements in the array
	 * @param <A> the type of the array
	 * @return the singleton instance
	 */
	public static <E, A> PrimitiveArrayVerifierNoOp<E, A> getInstance()
	{
		@SuppressWarnings("unchecked")
		PrimitiveArrayVerifierNoOp<E, A> result = (PrimitiveArrayVerifierNoOp<E, A>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveArrayVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveArrayVerifier<E, A> getThis()
	{
		return this;
	}
}
