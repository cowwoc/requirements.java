/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
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
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveNumberVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveNumberVerifier<T> getThis()
	{
		return this;
	}
}
