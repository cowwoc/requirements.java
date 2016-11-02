/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import static org.bitbucket.cowwoc.requirements.util.ConsoleConstants.LINE_LENGTH;

/**
 * Default implementation of ComparableRequirements.
 * <p>
 * @param <T> the type of objects that the parameter may be compared to
 * @author Gili Tzabari
 */
final class ComparableRequirementsImpl<T extends Comparable<? super T>>
	implements ComparableRequirements<T>
{
	private final SingletonScope scope;
	private final T parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<T> asObject;

	/**
	 * Creates new ComparableRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	ComparableRequirementsImpl(SingletonScope scope, T parameter, String name, Configuration config)
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
	public ComparableRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ComparableRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ComparableRequirements<T> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ComparableRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ComparableRequirements<T> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ComparableRequirementsImpl<>(scope, parameter, name, newConfig);
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value)
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value, String name)
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value)
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value, String name)
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableRequirements<T> isIn(Collection<T> collection)
	{
		asObject.isIn(collection);
		return this;
	}

	@Override
	public ComparableRequirements<T> isInstanceOf(Class<?> type)
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNull()
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotNull()
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = parameter.compareTo(value);
		if (difference < 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be less than %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Max", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		int difference = parameter.compareTo(value);
		if (difference < 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be less than %s", this.name, value)).
			addContext("Actual", parameter).
			addContext("Max", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = parameter.compareTo(value);
		if (difference <= 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Max", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		int difference = parameter.compareTo(value);
		if (difference <= 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s", name, value)).
			addContext("Actual", parameter).
			addContext("Max", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = parameter.compareTo(value);
		if (difference > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be greater than %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Min", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		int difference = parameter.compareTo(value);
		if (difference > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be greater than %s", name, value)).
			addContext("Actual", parameter).
			addContext("Min", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = parameter.compareTo(value);
		if (difference >= 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s", this.name, name)).
			addContext("Actual", parameter).
			addContext("Min", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		int difference = parameter.compareTo(value);
		if (difference >= 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s", name, value)).
			addContext("Actual", parameter).
			addContext("Min", value).
			build();
	}

	@Override
	public ComparableRequirements<T> isIn(T first, T last)
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter.compareTo(first) >= 0 && parameter.compareTo(last) <= 0)
			return this;
		StringBuilder message = new StringBuilder(LINE_LENGTH);
		message.append(name).append(" must be in the range [").append(first).append(", ").append(last).
			append("]\n").
			append(String.format("Actual: %s", parameter));
		throw config.exceptionBuilder(IllegalArgumentException.class, message.toString()).
			build();
	}

	@Override
	public ComparableRequirements<T> isolate(Consumer<ComparableRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
