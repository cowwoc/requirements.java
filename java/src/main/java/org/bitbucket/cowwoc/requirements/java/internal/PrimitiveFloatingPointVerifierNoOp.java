/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
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
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public PrimitiveFloatingPointVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected PrimitiveFloatingPointVerifier<T> getThis()
	{
		return this;
	}
}
