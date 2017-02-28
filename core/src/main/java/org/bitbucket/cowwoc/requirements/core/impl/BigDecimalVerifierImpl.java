/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@code BigDecimalVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalVerifierImpl
	extends NumberCapabilitiesImpl<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	/**
	 * Creates new BigDecimalVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public BigDecimalVerifierImpl(ApplicationScope scope, BigDecimal actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier isZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() == 0)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be zero", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isNotZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() != 0)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return new BigDecimalPrecisionVerifierImpl(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		consumer.accept(precision());
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> scale()
	{
		return new BigDecimalScaleVerifierImpl(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(scale());
		return this;
	}
}
