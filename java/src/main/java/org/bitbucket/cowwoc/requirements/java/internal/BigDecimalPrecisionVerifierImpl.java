/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.math.BigDecimal;

/**
 * Default implementation of {@link BigDecimalPrecisionVerifier}.
 */
public final class BigDecimalPrecisionVerifierImpl
	extends NumberCapabilitiesImpl<BigDecimalPrecisionVerifier, Integer>
	implements BigDecimalPrecisionVerifier
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected BigDecimalPrecisionVerifierImpl(ApplicationScope scope, String name, BigDecimal actual,
	                                          Configuration config)
	{
		super(scope, name + ".precision()", actual.precision(), config);
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be zero", null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotZero()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be zero", null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be zero or negative", null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isPositive()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be zero or negative", null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be negative", null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " can never be negative", null).
			build();
	}
}
