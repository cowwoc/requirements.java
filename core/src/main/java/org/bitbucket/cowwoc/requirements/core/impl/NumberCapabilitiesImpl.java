/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.capabilities.NumberCapabilities;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link NumberCapabilities}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class NumberCapabilitiesImpl<S, T extends Number & Comparable<? super T>>
	extends ComparableCapabilitiesImpl<S, T>
	implements NumberCapabilities<S, T>
{
	/**
	 * Creates new NumberCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public NumberCapabilitiesImpl(ApplicationScope scope, T actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public S isNegative()
	{
		if (actual.longValue() < 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNegative()
	{
		if (actual.longValue() >= 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isZero()
	{
		if (actual.longValue() == 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be zero.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotZero()
	{
		if (actual.longValue() != 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public S isPositive()
	{
		if (actual.longValue() > 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotPositive()
	{
		if (actual.longValue() <= 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be positive.", name)).
			addContext("Actual", actual).
			build();
	}
}
