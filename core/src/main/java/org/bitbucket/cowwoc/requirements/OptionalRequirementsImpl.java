/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of OptionalRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class OptionalRequirementsImpl implements OptionalRequirements
{
	private final SingletonScope scope;
	private final Optional<?> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Optional<?>> asObject;

	/**
	 * Creates new OptionalRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	OptionalRequirementsImpl(SingletonScope scope, Optional<?> parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(scope, parameter, name, config);
	}

	@Override
	public OptionalRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new OptionalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public OptionalRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new OptionalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public OptionalRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new OptionalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public OptionalRequirements isPresent()
	{
		if (parameter.isPresent())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be present", name)).
			build();
	}

	@Override
	public OptionalRequirements isEmpty()
	{
		if (!parameter.isPresent())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty", name)).
			build();
	}

	@Override
	public OptionalRequirements isNotNull()
	{
		// Always true
		return this;
	}

	@Override
	public OptionalRequirements isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public OptionalRequirements isIn(Collection<Optional<?>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public OptionalRequirements isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalRequirements isNotEqualTo(Optional<?> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalRequirements isEqualTo(Optional<?> value)
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
