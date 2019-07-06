/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.extension.AbstractComparableVerifierNoOp;

/**
 * An implementation of {@code ComparableVerifier} that does nothing.
 *
 * @param <T> the type of the value
 */
public final class ComparableVerifierNoOp<T extends Comparable<? super T>>
	extends AbstractComparableVerifierNoOp<ComparableVerifier<T>, T>
	implements ComparableVerifier<T>
{
	/**
	 * @param config the instance configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public ComparableVerifierNoOp(Configuration config)
	{
		super(config);
	}

	@Override
	protected ComparableVerifier<T> getThis()
	{
		return this;
	}
}
