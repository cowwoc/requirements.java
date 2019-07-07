/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code ArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierNoOp<E>
	extends AbstractArrayVerifierNoOp<ArrayVerifier<E>, E, E[]>
	implements ArrayVerifier<E>
{
	private static final ArrayVerifierNoOp<?> INSTANCE = new ArrayVerifierNoOp<>();

	/**
	 * @param <E> the type of elements in the array
	 * @return the singleton instance
	 */
	public static <E> ArrayVerifierNoOp<E> getInstance()
	{
		@SuppressWarnings("unchecked")
		ArrayVerifierNoOp<E> result = (ArrayVerifierNoOp<E>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ArrayVerifierNoOp()
	{
	}

	@Override
	protected ArrayVerifier<E> getThis()
	{
		return this;
	}
}
