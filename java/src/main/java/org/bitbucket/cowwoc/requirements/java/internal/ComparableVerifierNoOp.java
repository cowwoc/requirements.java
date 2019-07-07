/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * A {@code ComparableVerifier} that does nothing.
 *
 * @param <T> the type of the value being verified
 */
public final class ComparableVerifierNoOp<T extends Comparable<? super T>>
	extends AbstractComparableVerifierNoOp<ComparableVerifier<T>, T>
	implements ComparableVerifier<T>
{
	private static final ComparableVerifierNoOp<?> INSTANCE = new ComparableVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T extends Comparable<? super T>> ComparableVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		ComparableVerifierNoOp<T> result = (ComparableVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ComparableVerifierNoOp()
	{
	}

	@Override
	protected ComparableVerifier<T> getThis()
	{
		return this;
	}
}
