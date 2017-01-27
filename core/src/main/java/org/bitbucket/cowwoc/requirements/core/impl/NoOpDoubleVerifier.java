/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.DoubleVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of DoubleVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpDoubleVerifier implements DoubleVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpDoubleVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	public DoubleVerifier isNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNumber()
	{
		return this;
	}

	@Override
	public DoubleVerifier isFinite()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotFinite()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNegative()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotZero()
	{
		return this;
	}

	@Override
	public DoubleVerifier isPositive()
	{
		return this;
	}

	@Override
	public DoubleVerifier isZero()
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isBetween(Double min, Double max)
	{
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value, String name)
	{
		return this;
	}

	@Override
	public DoubleVerifier isIn(Collection<Double> collection)
	{
		return this;
	}

	@Override
	public DoubleVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public DoubleVerifier isNull()
	{
		return this;
	}

	@Override
	public DoubleVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public DoubleVerifier asString(Consumer<StringVerifier> consumer)
	{
		return this;
	}

	@Override
	public Optional<Double> getActualIfPresent()
	{
		return Optional.empty();
	}

	@Override
	public Double getActual()
	{
		throw new NoSuchElementException("Assertions are disabled");
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public DoubleVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
