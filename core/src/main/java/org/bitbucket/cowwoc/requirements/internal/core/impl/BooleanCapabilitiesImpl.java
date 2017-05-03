/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.BooleanCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link BooleanCapabilities}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @author Gili Tzabari
 */
public abstract class BooleanCapabilitiesImpl<S>
	extends ComparableCapabilitiesImpl<S, Boolean>
	implements BooleanCapabilities<S>
{
	/**
	 * Creates new ComparableCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public BooleanCapabilitiesImpl(ApplicationScope scope, Boolean actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public S isTrue()
	{
		if (actual)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be true", name)).
			build();
	}

	@Override
	public S isFalse()
	{
		if (!actual)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be false", name)).
			build();
	}
}