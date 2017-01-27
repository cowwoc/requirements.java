/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.OptionalVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of OptionalVerifier.
 *
 * @author Gili Tzabari
 */
public final class OptionalVerifierImpl implements OptionalVerifier
{
	private final ApplicationScope scope;
	private final Optional<?> actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<Optional<?>> asObject;

	/**
	 * Creates new OptionalVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public OptionalVerifierImpl(ApplicationScope scope, Optional<?> actual, String name,
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
	public OptionalVerifier isPresent()
	{
		if (actual.isPresent())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be present", name)).
			build();
	}

	@Override
	public OptionalVerifier isEmpty()
	{
		if (!actual.isPresent())
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be empty.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public OptionalVerifier contains(Object value)
	{
		if (value == null)
			return isEmpty();
		Optional<?> expected = Optional.of(value);
		if (actual.equals(expected))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public OptionalVerifier contains(Object expected, String name)
	{
		Optional<?> expectedOptional = Optional.ofNullable(expected);
		if (actual.equals(expectedOptional))
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must contain %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Expected", expectedOptional).
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
	public OptionalVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
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
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public OptionalVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
