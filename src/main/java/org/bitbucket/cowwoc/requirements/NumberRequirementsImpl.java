/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.function.Consumer;
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
	private final T parameter;
	private final String name;
	private final Configuration config;
	private final ComparableRequirements<T> asComparable;

	/**
	 * Creates a new NumberRequirementsImpl.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	NumberRequirementsImpl(T parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asComparable = new ComparableRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public NumberRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new NumberRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value) throws IllegalArgumentException
	{
		asComparable.isEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value) throws IllegalArgumentException
	{
		asComparable.isNotEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isNotEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isInstanceOf(type);
		return this;
	}

	@Override
	public NumberRequirements<T> isNull() throws IllegalArgumentException
	{
		asComparable.isNull();
		return this;
	}

	@Override
	public NumberRequirements<T> isNotNull() throws NullPointerException
	{
		asComparable.isNotNull();
		return this;
	}

	@Override
	public NumberRequirements<T> isNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() < 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be negative.\n" +
				"Actual: %s", name, parameter));
	}

	@Override
	public NumberRequirements<T> isNotNegative() throws IllegalArgumentException
	{
		if (parameter.longValue() >= 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be negative.", name),
			"Actual", parameter);
	}

	@Override
	public NumberRequirements<T> isZero() throws IllegalArgumentException
	{
		if (parameter.longValue() == 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be zero.", name),
			"Actual", parameter);
	}

	@Override
	public NumberRequirements<T> isNotZero() throws IllegalArgumentException
	{
		if (parameter.longValue() != 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be zero", name));
	}

	@Override
	public NumberRequirements<T> isPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() > 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be positive.", name),
			"Actual", parameter);
	}

	@Override
	public NumberRequirements<T> isNotPositive() throws IllegalArgumentException
	{
		if (parameter.longValue() <= 0L)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may not be positive.", name),
			"Actual", parameter);
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThan(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThan(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isGreaterThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThan(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThan(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public NumberRequirements<T> isLessThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public NumberRequirements<T> isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		asComparable.isIn(range);
		return this;
	}

	@Override
	public NumberRequirements<T> isolate(Consumer<NumberRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}