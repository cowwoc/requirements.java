/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code ClassRequirements}.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
final class ClassRequirementsImpl<T> implements ClassRequirements<T>
{
	private final SingletonScope scope;
	private final Class<T> actual;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Class<T>> asObject;

	/**
	 * Creates new ClassRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	ClassRequirementsImpl(SingletonScope scope, Class<T> actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(scope, actual, name, config);
	}

	@Override
	public ClassRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ClassRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public ClassRequirements<T> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ClassRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public ClassRequirements<T> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ClassRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public ClassRequirements<T> isEqualTo(Class<T> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ClassRequirements<T> isEqualTo(Class<T> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ClassRequirements<T> isNotEqualTo(Class<T> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ClassRequirements<T> isNotEqualTo(Class<T> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ClassRequirements<T> isIn(Collection<Class<T>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public ClassRequirements<T> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ClassRequirements<T> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ClassRequirements<T> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ClassRequirements<T> isSupertypeOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat(type, "type").isNotNull();
		if (actual.isAssignableFrom(type))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be a supertype of %s.", name, type)).
			addContext("Actual", actual.getClass()).
			build();
	}

	@Override
	public ClassRequirements<T> isolate(Consumer<ClassRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
