/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

/**
 * A {@code PrimitiveIntegerVerifier} that does nothing.
 *
 * @param <T> the type of the integer number
 */
public final class PrimitiveIntegerVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<PrimitiveIntegerVerifier<T>, T>
	implements PrimitiveIntegerVerifier<T>
{
	private static final PrimitiveIntegerVerifierNoOp<?> INSTANCE = new PrimitiveIntegerVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> PrimitiveIntegerVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		PrimitiveIntegerVerifierNoOp<T> result = (PrimitiveIntegerVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveIntegerVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveIntegerVerifier<T> getThis()
	{
		return this;
	}
}
