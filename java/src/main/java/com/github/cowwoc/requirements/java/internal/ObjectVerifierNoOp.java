/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal;

import com.github.cowwoc.requirements.java.ObjectVerifier;
import com.github.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

/**
 * An {@code ObjectVerifier} that does nothing.
 *
 * @param <T> the type of the value being verified
 */
public final class ObjectVerifierNoOp<T> extends AbstractObjectVerifierNoOp<ObjectVerifier<T>, T>
	implements ObjectVerifier<T>
{
	private static final ObjectVerifierNoOp<?> INSTANCE = new ObjectVerifierNoOp<>();

	/**
	 * @param <T> the type of the value being verified
	 * @return the singleton instance
	 */
	public static <T> ObjectVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		ObjectVerifierNoOp<T> result = (ObjectVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ObjectVerifierNoOp()
	{
	}

	@Override
	protected ObjectVerifierNoOp<T> getThis()
	{
		return this;
	}
}
