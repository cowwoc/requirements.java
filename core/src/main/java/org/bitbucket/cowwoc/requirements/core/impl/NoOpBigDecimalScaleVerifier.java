/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalScaleVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of BigDecimalScaleVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpBigDecimalScaleVerifier implements BigDecimalScaleVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpBigDecimalScaleVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	@Deprecated
	public BigDecimalScaleVerifier isNull()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isPositive()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isZero()
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isBetween(Integer min, Integer max)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public BigDecimalScaleVerifier asString(Consumer<StringVerifier> consumer)
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
	public BigDecimalScaleVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
