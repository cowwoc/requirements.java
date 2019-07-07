/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.IntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

/**
 * An {@code IntegerVerifier} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class IntegerVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<IntegerVerifier<T>, T>
	implements IntegerVerifier<T>
{
	private static final IntegerVerifierNoOp<?> INSTANCE = new IntegerVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> IntegerVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		IntegerVerifierNoOp<T> result = (IntegerVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private IntegerVerifierNoOp()
	{
	}

	@Override
	protected IntegerVerifier<T> getThis()
	{
		return this;
	}
}
