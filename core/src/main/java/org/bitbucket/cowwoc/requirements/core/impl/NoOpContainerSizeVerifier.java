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
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * An implementation of ContainerSizeVerifier that does nothing.
 *
 * @author Gili Tzabari
 */
public final class NoOpContainerSizeVerifier implements ContainerSizeVerifier
{
	private final Configuration config;

	/**
	 * @param config the verifier's configuration
	 * @throws AssertionError if {@code config} is null
	 */
	public NoOpContainerSizeVerifier(Configuration config)
	{
		assert (config != null): "config may not be null";
		this.config = config;
	}

	@Override
	@Deprecated
	public ContainerSizeVerifier isNull()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotNegative()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotZero()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isPositive()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isZero()
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isGreaterThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThan(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThan(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isLessThanOrEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isBetween(Integer min, Integer max)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotEqualTo(Integer value)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotEqualTo(Integer value, String name)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isIn(Collection<Integer> collection)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isInstanceOf(Class<?> type)
	{
		return this;
	}

	@Override
	public ContainerSizeVerifier isNotNull()
	{
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new NoOpStringVerifier(config);
	}

	@Override
	public ContainerSizeVerifier asString(Consumer<StringVerifier> consumer)
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
	public ContainerSizeVerifier configuration(Consumer<Configuration> consumer)
	{
		return this;
	}
}
