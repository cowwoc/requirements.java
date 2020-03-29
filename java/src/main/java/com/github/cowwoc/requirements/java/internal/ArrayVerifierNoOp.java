/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An {@code ArrayVerifier} that does nothing.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierNoOp<A, E>
	extends AbstractArrayVerifierNoOp<ArrayVerifier<A, E>, A, E>
	implements ArrayVerifier<A, E>
{
	private static final ArrayVerifierNoOp<?, ?> INSTANCE = new ArrayVerifierNoOp<>();

	/**
	 * @param <A> the type of the array
	 * @param <E> the type of elements in the array
	 * @return the singleton instance
	 */
	public static <A, E> ArrayVerifierNoOp<A, E> getInstance()
	{
		@SuppressWarnings("unchecked")
		ArrayVerifierNoOp<A, E> result = (ArrayVerifierNoOp<A, E>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ArrayVerifierNoOp()
	{
	}

	@Override
	protected ArrayVerifier<A, E> getThis()
	{
		return this;
	}
}
