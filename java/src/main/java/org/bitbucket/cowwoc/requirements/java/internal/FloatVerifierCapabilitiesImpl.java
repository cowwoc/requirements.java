/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link FloatingPointCapabilities} for {@code float}s.
 *
 * @param <S> the type of verifier that methods should return
 */
public abstract class FloatVerifierCapabilitiesImpl<S>
	extends NumberCapabilitiesImpl<S, Float>
	implements FloatingPointCapabilities<S, Float>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected FloatVerifierCapabilitiesImpl(ApplicationScope scope, String name, Float actual,
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
			name + " must be a number.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNumber()
	{
		if (actual.isNaN())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be a number.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isFinite()
	{
		if (!actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be finite.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotFinite()
	{
		if (actual.isInfinite())
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be infinite.").
			addContext("Actual", actual).
			build();
	}
}
