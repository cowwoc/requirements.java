/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

/**
 * A {@code ClassVerifier} that does nothing.
 *
 * @param <T> the type of the class
 */
public final class ClassVerifierNoOp<T>
	extends AbstractObjectVerifierNoOp<ClassVerifier<T>, Class<T>>
	implements ClassVerifier<T>
{
	private static final ClassVerifierNoOp<?> INSTANCE = new ClassVerifierNoOp<>();

	/**
	 * @param <T> the type of the class
	 * @return the singleton instance
	 */
	public static <T> ClassVerifierNoOp<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		ClassVerifierNoOp<T> result = (ClassVerifierNoOp<T>) INSTANCE;
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private ClassVerifierNoOp()
	{
	}

	@Override
	protected ClassVerifier<T> getThis()
	{
		return this;
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		return this;
	}
}
