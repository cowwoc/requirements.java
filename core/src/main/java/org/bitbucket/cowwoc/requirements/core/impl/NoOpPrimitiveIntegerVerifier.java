/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of PrimitiveIntegerVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpPrimitiveIntegerVerifier implements PrimitiveIntegerVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpPrimitiveIntegerVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	@Deprecated
	public PrimitiveIntegerVerifier isNull()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNegative()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotZero()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isPositive()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isZero()
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isBetween(Integer min, Integer max)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public PrimitiveIntegerVerifier asString(Consumer<StringVerifier> consumer)
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
}
