/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Default implementation of MapSizeRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class MapSizeRequirementsImpl implements MapSizeRequirements
{
	/**
	 * @param amount an amount
	 * @return "entry" or "entries" depending on whether {@code amount} is plural
	 */
	private static String getSingularOrPlural(int amount)
	{
		if (amount == 1)
			return "entry";
		return "entries";
	}
	private final Map<?, ?> map;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new MapSizeRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	MapSizeRequirementsImpl(Map<?, ?> parameter, String name,
		Configuration config) throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.map = parameter;
		this.parameter = parameter.size();
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	@Override
	public MapSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapSizeRequirementsImpl(map, name, newConfig);
	}

	@Override
	public MapSizeRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapSizeRequirementsImpl(map, name, newConfig);
	}

	@Override
	public MapSizeRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapSizeRequirementsImpl(map, name, newConfig);
	}

	@Override
	@Deprecated
	public MapSizeRequirements isNull() throws IllegalArgumentException
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be null", name), null);
	}

	@Override
	public MapSizeRequirements isNotNull() throws NullPointerException
	{
		// Always true
		return this;
	}

	@Override
	public MapSizeRequirements isIn(Collection<Integer> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public MapSizeRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public MapSizeRequirements isolate(Consumer<MapSizeRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) entries. It contained %,d entries.",
				this.name, name, value, parameter)).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %s (%,d) %s. It contained %,d %s.", this.name,
				name, value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) %s. It contained %,d %s.",
				this.name, name, value, getSingularOrPlural(value), parameter, getSingularOrPlural(value))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public MapSizeRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one entry. It contained %,d entries.", name,
				parameter)).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public MapSizeRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public MapSizeRequirements isNegative() throws IllegalArgumentException
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never have a negative size", name), null);
	}

	@Deprecated
	@Override
	public MapSizeRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" entries. It contained %,d %s.", parameter,
			getSingularOrPlural(parameter)));
		throw config.exceptionBuilder(IllegalArgumentException.class, message.toString()).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isIn(Integer first, Integer last)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		return isIn(Range.closed(first, last));
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value, getSingularOrPlural(value))).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name, name, value,
				getSingularOrPlural(value))).
			addContext("Actual", map).
			build();
	}
}
