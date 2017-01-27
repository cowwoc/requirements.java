/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of BigDecimalPrecisionVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpBigDecimalPrecisionVerifier implements BigDecimalPrecisionVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBigDecimalPrecisionVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
	{
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isBetween(Integer min, Integer max)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public BigDecimalPrecisionVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Integer> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Integer getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public BigDecimalPrecisionVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
