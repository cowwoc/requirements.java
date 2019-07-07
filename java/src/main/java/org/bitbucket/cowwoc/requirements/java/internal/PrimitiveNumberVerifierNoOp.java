/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractPrimitiveNumberVerifierNoOp;

/**
 * A {@code PrimitiveNumberVerifier} that does nothing.
 *
 * @param <T> the type of the value being verified
 */
public final class PrimitiveNumberVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractPrimitiveNumberVerifierNoOp<PrimitiveNumberVerifier<T>, T>
	implements PrimitiveNumberVerifier<T>
{
	private static final PrimitiveNumberVerifierNoOp<?> INSTANCE = new PrimitiveNumberVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> PrimitiveNumberVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		PrimitiveNumberVerifierNoOp<T> result = (PrimitiveNumberVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveNumberVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveNumberVerifier<T> getThis()
	{
		return this;
	}
}
