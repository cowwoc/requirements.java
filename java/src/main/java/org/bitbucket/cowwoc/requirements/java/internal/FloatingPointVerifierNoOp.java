/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.FloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractFloatingPointVerifierNoOp;

/**
 * A {@code FloatingPointVerifier} that does nothing.
 *
 * @param <T> the type of the floating-point number
 */
public final class FloatingPointVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractFloatingPointVerifierNoOp<FloatingPointVerifier<T>, T>
	implements FloatingPointVerifier<T>
{
	private static final FloatingPointVerifierNoOp<?> INSTANCE = new FloatingPointVerifierNoOp<>();

	/**
	 * @param <T> the type of the floating-point number
	 * @return the singleton instance
	 */
	public static <T extends Number & Comparable<? super T>> FloatingPointVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		FloatingPointVerifierNoOp<T> result = (FloatingPointVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private FloatingPointVerifierNoOp()
	{
	}

	@Override
	protected FloatingPointVerifier<T> getThis()
	{
		return this;
	}
}
