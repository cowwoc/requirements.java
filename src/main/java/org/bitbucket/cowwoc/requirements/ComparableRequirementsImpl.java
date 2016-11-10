/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
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
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	ComparableRequirementsImpl(T parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
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
	public ComparableRequirements<T> addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new ComparableRequirementsImpl<>(parameter, name, newConfig);
	}

	@Override
	public ComparableRequirements<T> withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
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
	public ComparableRequirements<T> isIn(Collection<T> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asObject.isIn(collection);
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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
		throws NullPointerException, IllegalArgumentException
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

	@Deprecated
	@Override
	public ComparableRequirements<T> isIn(Range<T> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			Ranges.getExceptionMessage(name, parameter, range)).
			build();
	}

	@Override
	public ComparableRequirements<T> isIn(T first, T last)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter.compareTo(first) >= 0 && parameter.compareTo(last) <= 0)
			return this;
		return isIn(Range.closed(first, last));
	}

	@Override
	public ComparableRequirements<T> isolate(Consumer<ComparableRequirements<T>> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
