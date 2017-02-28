/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * An implementation of {@code PrimitiveNumberVerifier} for a
 * {@code BigDecimal}'s scale.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalScaleVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveNumberVerifier<Integer>, Integer>
	implements PrimitiveNumberVerifier<Integer>
{
	/**
	 * Creates new BigDecimalScaleVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config}
	 *                        are null; if {@code name} is empty
	 */
	public BigDecimalScaleVerifierImpl(ApplicationScope scope, BigDecimal actual, String name,
		Configuration config)
	{
		super(scope, actual.scale(), name + ".scale()", config);
	}

	@Override
	@Deprecated
	public PrimitiveNumberVerifier<Integer> isNull()
	{
		return super.isNull();
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isZero()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be zero", name), null).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNotPositive()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null).
			build();
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isPositive()
	{
		// Always true
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveNumberVerifier<Integer> isNegative()
	{
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s can never be negative", name), null).
			build();
	}
}
