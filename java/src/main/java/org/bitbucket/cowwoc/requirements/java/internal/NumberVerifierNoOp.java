/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.NumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

/**
 * A {@code NumberVerifier} that does nothing.
 *
 * @param <T> the type of the value being verified
 */
public final class NumberVerifierNoOp<T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<NumberVerifier<T>, T>
	implements NumberVerifier<T>
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NumberVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected NumberVerifier<T> getThis()
	{
		return this;
	}
}
