/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;

/**
 * An implementation of {@link ComparableVerifier} that does nothing.
 *
 * @param <T> the type of the value
 */
public final class NoOpComparableVerifier<T extends Comparable<? super T>>
	extends NoOpComparableCapabilities<ComparableVerifier<T>, T>
	implements ComparableVerifier<T>
{
	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpComparableVerifier(Configuration config)
	{
		super(config);
	}

	@Override
	protected ComparableVerifier<T> getThis()
	{
		return this;
	}
}
