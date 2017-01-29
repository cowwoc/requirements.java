/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * An implementation of {@code PrimitiveIntegerVerifier} for a
 * {@code BigDecimal}'s scale.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalScaleVerifierImpl
	extends NumberCapabilitiesImpl<PrimitiveIntegerVerifier, Integer>
	implements PrimitiveIntegerVerifier
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
	public PrimitiveIntegerVerifier isNull()
	{
		return super.isNull();
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isZero()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be zero", name), null);
	}

	@Override
	public PrimitiveIntegerVerifier isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNotPositive()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null);
	}

	@Override
	public PrimitiveIntegerVerifier isPositive()
	{
		// Always true
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be negative", name), null);
	}
}
