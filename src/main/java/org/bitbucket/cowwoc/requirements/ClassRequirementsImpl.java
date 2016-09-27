/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code ClassRequirements}.
 * <p>
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
final class ClassRequirementsImpl<T>
	implements ClassRequirements<T>
{
	private final Class<T> parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<Class<T>> asObject;

	/**
	 * Creates new ClassRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ClassRequirementsImpl(Class<T> parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public ClassRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ClassRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ClassRequirements<T> addContext(String key, Object value) throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new ClassRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ClassRequirements<T> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ClassRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ClassRequirements<T> isEqualTo(Class<T> value) throws IllegalArgumentException
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ClassRequirements<T> isEqualTo(Class<T> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ClassRequirements<T> isNotEqualTo(Class<T> value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ClassRequirements<T> isNotEqualTo(Class<T> value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ClassRequirements<T> isIn(Collection<Class<T>> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public ClassRequirements<T> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ClassRequirements<T> isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ClassRequirements<T> isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ClassRequirements<T> isSupertypeOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(type, "type").isNotNull();
		if (parameter.isAssignableFrom(type))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be a supertype of %s", name, type)).
			addContext("Actual", parameter.getClass()).
			build();
	}

	@Override
	public ClassRequirements<T> isolate(Consumer<ClassRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
