/*
 * Copyright 2015 Gili Tzabaro.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of BigDecimalVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpBigDecimalVerifier implements BigDecimalVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBigDecimalVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return new NoOpBigDecimalPrecisionVerifier(config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier scale()
	{
		return new NoOpPrimitiveIntegerVerifier(config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isZero()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isBetween(BigDecimal min, BigDecimal max)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isIn(Collection<BigDecimal> collection)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public BigDecimalVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<BigDecimal> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public BigDecimal getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}
}
