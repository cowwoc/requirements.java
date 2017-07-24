/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link FloatingPointCapabilities} for {@code double}s.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @author Gili Tzabari
 */
public abstract class DoubleVerifierCapabilitiesImpl<S>
	extends NumberCapabilitiesImpl<S, Double>
	implements FloatingPointCapabilities<S, Double>
{
	/**
	 * Creates new DoubleVerifierCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public DoubleVerifierCapabilitiesImpl(ApplicationScope scope, String name, Double actual,
		Configuration config)
	{
		super(scope, name, actual, config);
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
