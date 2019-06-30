/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ClassVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractObjectVerifierNoOp;

/**
 * An implementation of {@code ClassVerifier} that does nothing.
 *
 * @param <T> the type of the class
 */
public final class ClassVerifierNoOp<T>
	extends AbstractObjectVerifierNoOp<ClassVerifier<T>, Class<T>>
	implements ClassVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public ClassVerifierNoOp(Configuration config)
	{
		super(config);
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
