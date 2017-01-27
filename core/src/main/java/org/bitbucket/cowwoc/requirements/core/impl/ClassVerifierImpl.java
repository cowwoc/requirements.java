/*
 * Copyright 2013 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ClassVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of {@code ClassVerifier}.
 *
 * @param <T> the type of the class
 * @author Gili Tzabari
 */
public final class ClassVerifierImpl<T> implements ClassVerifier<T>
{
	private final ApplicationScope scope;
	private final Class<T> actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<Class<T>> asObject;

	/**
	 * Creates new ClassVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ClassVerifierImpl(ApplicationScope scope, Class<T> actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asObject = new ObjectVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public ClassVerifier<T> isEqualTo(Class<T> value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ClassVerifier<T> isEqualTo(Class<T> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ClassVerifier<T> isNotEqualTo(Class<T> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ClassVerifier<T> isNotEqualTo(Class<T> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ClassVerifier<T> isIn(Collection<Class<T>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public ClassVerifier<T> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ClassVerifier<T> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ClassVerifier<T> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ClassVerifier<T> isSupertypeOf(Class<?> type)
	{
		scope.getInternalVerifier().requireThat(type, "type").isNotNull();
		if (actual.isAssignableFrom(type))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be a supertype of %s.", name, type)).
			addContext("Actual", actual.getClass()).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public ClassVerifier<T> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<Class<T>> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public Class<T> getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public ClassVerifier<T> configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
