/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractArrayVerifierNoOp;

/**
 * An implementation of {@code ArrayVerifier} that does nothing.
 *
 * @param <E> the type of elements in the array
 */
public final class ArrayVerifierNoOp<E>
	extends AbstractArrayVerifierNoOp<ArrayVerifier<E>, E, E[]>
	implements ArrayVerifier<E>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public ArrayVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected ArrayVerifier<E> getThis()
	{
		return this;
	}
}
