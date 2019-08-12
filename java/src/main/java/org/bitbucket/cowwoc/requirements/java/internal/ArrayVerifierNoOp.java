/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An {@code ArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 * @param <A> the type of the array
 */
public final class ArrayVerifierNoOp<E, A>
	extends AbstractArrayVerifierNoOp<ArrayVerifier<E, A>, E, A>
	implements ArrayVerifier<E, A>
{
	private static final ArrayVerifierNoOp<?, ?> INSTANCE = new ArrayVerifierNoOp<>();

	/**
	 * @param <E> the type of elements in the array
	 * @param <A> the type of the array
	 * @return the singleton instance
	 */
	public static <E, A> ArrayVerifierNoOp<E, A> getInstance()
	{
		@SuppressWarnings("unchecked")
		ArrayVerifierNoOp<E, A> result = (ArrayVerifierNoOp<E, A>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ArrayVerifierNoOp()
	{
	}

	@Override
	protected ArrayVerifier<E, A> getThis()
	{
		return this;
	}
}
