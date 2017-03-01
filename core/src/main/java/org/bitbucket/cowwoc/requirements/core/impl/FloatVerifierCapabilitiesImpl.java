/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link FloatingPointCapabilities} for {@code float}s.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @author Gili Tzabari
 */
public abstract class FloatVerifierCapabilitiesImpl<S>
	extends NumberCapabilitiesImpl<S, Float>
	implements FloatingPointCapabilities<S, Float>
{
	/**
	 * Creates new FloatVerifierCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public FloatVerifierCapabilitiesImpl(ApplicationScope scope, Float actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public S isNumber()
	{
		if (!actual.isNaN())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNumber()
	{
		if (actual.isNaN())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isFinite()
	{
		if (!actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be finite.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotFinite()
	{
		if (actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be infinite.", name)).
			addContext("Actual", actual).
			build();
	}
}