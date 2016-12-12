/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;

/**
 * Default implementation of OptionalVerifier.
 *
 * @author Gili Tzabari
 */
public final class OptionalVerifierImpl implements OptionalVerifier
{
	private final SingletonScope scope;
	private final Optional<?> actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<Optional<?>> asObject;

	/**
	 * Creates new OptionalVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public OptionalVerifierImpl(SingletonScope scope, Optional<?> actual, String name,
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
	public OptionalVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new OptionalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public OptionalVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new OptionalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public OptionalVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new OptionalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public OptionalVerifier isPresent()
	{
		if (actual.isPresent())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be present", name)).
			build();
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		if (!actual.isPresent())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty", name)).
			build();
	}

	@Override
	public OptionalVerifier isNotNull()
	{
		// Always true
		return this;
	}

	@Override
	public OptionalVerifier isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public OptionalVerifier isIn(Collection<Optional<?>> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public OptionalVerifier isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalVerifier isNotEqualTo(Optional<?> value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public OptionalVerifier isEqualTo(Optional<?> value)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public Optional<Optional<?>> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public Optional<?> getActual()
	{
		return actual;
	}

	@Override
	public OptionalVerifier isolate(Consumer<OptionalVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
