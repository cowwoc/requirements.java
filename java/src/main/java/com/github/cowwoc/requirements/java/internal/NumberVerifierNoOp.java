/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.NumberVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

/**
 * A {@code NumberVerifier} that does nothing.
 *
 * @param <T> the type of the value being verified
 */
public final class NumberVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<NumberVerifier<T>, T>
	implements NumberVerifier<T>
{
	private static final NumberVerifierNoOp<?> INSTANCE = new NumberVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> NumberVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		NumberVerifierNoOp<T> result = (NumberVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private NumberVerifierNoOp()
	{
	}

	@Override
	protected NumberVerifier<T> getThis()
	{
		return this;
	}
}
