/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ComparableVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.util.ConsoleConstants.LINE_LENGTH;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Default implementation of ComparableVerifier.
 *
 * @param <T> the type of objects that the value may be compared to
 * @author Gili Tzabari
 */
public final class ComparableVerifierImpl<T extends Comparable<? super T>>
	implements ComparableVerifier<T>
{
	private final ApplicationScope scope;
	private final T actual;
	private final String name;
	private final Configuration config;
	private final ObjectVerifier<T> asObject;

	/**
	 * Creates new ComparableVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ComparableVerifierImpl(ApplicationScope scope, T actual, String name, Configuration config)
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
	public ComparableVerifier<T> isEqualTo(T value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ComparableVerifier<T> isEqualTo(T value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotEqualTo(T value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableVerifier<T> isIn(Collection<T> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public ComparableVerifier<T> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ComparableVerifier<T> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ComparableVerifier<T> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public ComparableVerifier<T> isLessThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public ComparableVerifier<T> isLessThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public ComparableVerifier<T> isGreaterThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public ComparableVerifier<T> isGreaterThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return this;
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public ComparableVerifier<T> isBetween(T min, T max)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(min, "min").isNotNull();
		verifier.requireThat(max, "max").isNotNull().isGreaterThanOrEqualTo(min, "min");
		if (actual.compareTo(min) >= 0 && actual.compareTo(max) <= 0)
			return this;
		StringBuilder message = new StringBuilder(LINE_LENGTH);
		message.append(name).append(" must be in range [").append(min).append(", ").append(max).
			append("]\n").
			append("Actual: ").append(actual);
		throw new ExceptionBuilder(config, IllegalArgumentException.class, message.toString()).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public ComparableVerifier<T> asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<T> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public T getActual()
	{
		return actual;
	}

	@Override
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public ComparableVerifier<T> configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
