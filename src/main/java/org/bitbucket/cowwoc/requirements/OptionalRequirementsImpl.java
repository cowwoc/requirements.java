/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of OptionalRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class OptionalRequirementsImpl implements OptionalRequirements
{
	private final Optional<?> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Optional<?>> asObject;

	/**
	 * Creates new OptionalRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	OptionalRequirementsImpl(Optional<?> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public OptionalRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new OptionalRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public OptionalRequirements withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new OptionalRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public OptionalRequirements isPresent() throws IllegalArgumentException
	{
		if (parameter.isPresent())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be present", name));
	}

	@Override
	public OptionalRequirements isEmpty() throws IllegalArgumentException
	{
		if (!parameter.isPresent())
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty", name));
	}

	@Override
	public OptionalRequirements isNotNull() throws NullPointerException
	{
		// Always true
		return this;
	}

	@Override
	public OptionalRequirements isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public OptionalRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value) throws IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalRequirements isolate(Consumer<OptionalRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
