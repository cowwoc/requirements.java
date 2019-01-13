/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.BooleanCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link BooleanCapabilities}.
 *
 * @param <S> the type of verifier that methods should return
 */
public abstract class BooleanCapabilitiesImpl<S>
	extends ComparableCapabilitiesImpl<S, Boolean>
	implements BooleanCapabilities<S>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected BooleanCapabilitiesImpl(ApplicationScope scope, String name, Boolean actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isTrue()
	{
		if (actual)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be true").
			build();
	}

	@Override
	public S isFalse()
	{
		if (!actual)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be false").
			build();
	}
}
