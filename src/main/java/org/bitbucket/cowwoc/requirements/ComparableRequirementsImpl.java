/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of ComparableRequirements.
 * <p>
 * @param <T> the type of objects that the parameter may be compared to
 * @author Gili Tzabari
 */
final class ComparableRequirementsImpl<T extends Comparable<? super T>>
	implements ComparableRequirements<T>
{
	private final T parameter;
	private final String name;
	private final Configuration config;
	private final ObjectRequirements<T> asObject;

	/**
	 * Creates new ComparableRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ComparableRequirementsImpl(T parameter, String name, Configuration config)
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
	public ComparableRequirements<T> withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ComparableRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value) throws IllegalArgumentException
	{
		asObject.isEqualTo(value);
		return this;
	}

	@Override
	public ComparableRequirements<T> isEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value) throws IllegalArgumentException
	{
		asObject.isNotEqualTo(value);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public ComparableRequirements<T> isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isInstanceOf(type);
		return this;
	}

	@Override
	public ComparableRequirements<T> isNull() throws IllegalArgumentException
	{
		asObject.isNull();
		return this;
	}

	@Override
	public ComparableRequirements<T> isNotNull() throws NullPointerException
	{
		asObject.isNotNull();
		return this;
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual < 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than %s (%s)", this.name, parameter, name, value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isLessThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual < 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than %s", this.name, parameter, value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual <= 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than or equal to %s (%s)", this.name, parameter, name,
				value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isLessThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual <= 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be less than or equal to %s", name, parameter, value),
			"Actual: %d", actual);
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual > 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than %s (%s)", this.name, parameter, name, value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isGreaterThan(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual > 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than %s", name, parameter, value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int actual = parameter.compareTo(value);
		if (actual >= 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than or equal to %s (%s)", this.name, parameter, name,
				value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isGreaterThanOrEqualTo(T value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		int actual = parameter.compareTo(value);
		if (actual >= 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s (%s) must be greater than or equal to %s", name, parameter, value),
			"Actual", actual);
	}

	@Override
	public ComparableRequirements<T> isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		throw config.createException(IllegalArgumentException.class,
			Ranges.getExceptionMessage(name, parameter, range));
	}

	@Override
	public ComparableRequirements<T> isolate(Consumer<ComparableRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
