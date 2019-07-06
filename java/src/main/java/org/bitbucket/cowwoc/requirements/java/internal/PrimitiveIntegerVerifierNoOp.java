/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
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
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveIntegerVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveIntegerVerifier<T> getThis()
	{
		return this;
	}
}
