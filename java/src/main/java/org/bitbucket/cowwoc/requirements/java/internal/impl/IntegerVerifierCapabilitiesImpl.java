/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.java.capabilities.IntegerCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link FloatingPointCapabilities} for {@code double}s.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class IntegerVerifierCapabilitiesImpl<S, T extends Number & Comparable<? super T>>
	extends NumberCapabilitiesImpl<S, T>
	implements IntegerCapabilities<S, T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	protected IntegerVerifierCapabilitiesImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isWholeNumber()
	{
		return getThis();
	}

	@Override
	public S isNotWholeNumber()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a whole number.", name)).
			addContext("Actual", actual).
			build();
	}
}
