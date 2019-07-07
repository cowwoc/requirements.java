/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.PrimitiveFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifierNoOp;

/**
 * A {@code PrimitiveFloatingPointVerifier} that does nothing.
 *
 * @param <T> the type of the floating-point number
 */
public final class PrimitiveFloatingPointVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractFloatingPointVerifierNoOp<PrimitiveFloatingPointVerifier<T>, T>
	implements PrimitiveFloatingPointVerifier<T>
{
	private static final PrimitiveFloatingPointVerifierNoOp<?> INSTANCE =
		new PrimitiveFloatingPointVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> PrimitiveFloatingPointVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		PrimitiveFloatingPointVerifierNoOp<T> result = (PrimitiveFloatingPointVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private PrimitiveFloatingPointVerifierNoOp()
	{
	}

	@Override
	protected PrimitiveFloatingPointVerifier<T> getThis()
	{
		return this;
	}
}
