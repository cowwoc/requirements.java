/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractNumberVerifierNoOp;

/**
 * An {@code ExtensibleIntegerVerifier} that does nothing.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractIntegerVerifierNoOp<S, T extends Number & Comparable<? super T>>
	extends AbstractNumberVerifierNoOp<S, T>
	implements ExtensibleIntegerVerifier<S, T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public AbstractIntegerVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		return getThis();
	}
}
