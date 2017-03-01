/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link BigDecimalPrecisionVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalPrecisionVerifierImpl
	extends NumberCapabilitiesImpl<BigDecimalPrecisionVerifier, Integer>
	implements BigDecimalPrecisionVerifier
{
	/**
	 * Creates new BigDecimalPrecisionVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public BigDecimalPrecisionVerifierImpl(ApplicationScope scope, BigDecimal actual, String name,
		Configuration config)
	{
		super(scope, actual.precision(), name + ".precision()", config);
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be zero", name), null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null).
			build();
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isPositive()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be negative", name), null).
			build();
	}
}
