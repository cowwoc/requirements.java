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

/**
 * Default implementation of {@code NumberRequirements}.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
final class NumberRequirementsImpl<T extends Number & Comparable<? super T>>
	implements NumberRequirements<T>
{
	private final SingletonScope scope;
	private final T actual;
	private final String name;
	private final Configuration config;
	private final ComparableRequirements<T> asComparable;

	/**
	 * Creates a new NumberRequirementsImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	NumberRequirementsImpl(SingletonScope scope, T actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asComparable = new ComparableRequirementsImpl<>(scope, actual, name, config);
	}

	@Override
	public NumberRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new NumberRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public NumberRequirements<T> addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new NumberRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public NumberRequirements<T> withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new NumberRequirementsImpl<>(scope, actual, name, newConfig);
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value)
	{
		asComparable.isEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value, String name)
	{
		asComparable.isEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value)
	{
		asComparable.isNotEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value, String name)
	{
		asComparable.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isIn(Collection<T> collection)
	{
		asComparable.isIn(collection);
		return this;
	}

	@Override
	public NumberRequirements<T> isInstanceOf(Class<?> type)
	{
		asComparable.isInstanceOf(type);
		return this;
	}

	@Override
	public NumberRequirements<T> isNull()
	{
		asComparable.isNull();
		return this;
	}

	@Override
	public NumberRequirements<T> isNotNull()
	{
		asComparable.isNotNull();
		return this;
	}

	@Override
	public NumberRequirements<T> isNegative()
	{
		if (actual.longValue() < 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberRequirements<T> isNotNegative()
	{
		if (actual.longValue() >= 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberRequirements<T> isZero()
	{
		if (actual.longValue() == 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be zero.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberRequirements<T> isNotZero()
	{
		if (actual.longValue() != 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public NumberRequirements<T> isPositive()
	{
		if (actual.longValue() > 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberRequirements<T> isNotPositive()
	{
		if (actual.longValue() <= 0L)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value, String name)
	{
		asComparable.isGreaterThan(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value)
	{
		asComparable.isGreaterThan(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value, String name)
	{
		asComparable.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value)
	{
		asComparable.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value, String name)
	{
		asComparable.isLessThan(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value)
	{
		asComparable.isLessThan(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value, String name)
	{
		asComparable.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value)
	{
		asComparable.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isIn(T first, T last)
	{
		asComparable.isIn(first, last);
		return this;
	}

	@Override
	public NumberRequirements<T> isolate(Consumer<NumberRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
