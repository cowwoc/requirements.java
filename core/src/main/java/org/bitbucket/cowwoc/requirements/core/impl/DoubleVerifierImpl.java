/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.DoubleVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of DoubleVerifier.
 *
 * @author Gili Tzabari
 */
public final class DoubleVerifierImpl
	extends NumberCapabilitiesImpl<DoubleVerifier, Double>
	implements DoubleVerifier
{
	/**
	 * Creates new DoubleVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public DoubleVerifierImpl(ApplicationScope scope, Double actual, String name, Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public DoubleVerifier isNumber()
	{
		if (!actual.isNaN())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isNotNumber()
	{
		if (actual.isNaN())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s may not be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isFinite()
	{
		if (!actual.isInfinite())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be finite.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isNotFinite()
	{
		if (actual.isInfinite())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be infinite.", name)).
			addContext("Actual", actual).
			build();
	}
}
