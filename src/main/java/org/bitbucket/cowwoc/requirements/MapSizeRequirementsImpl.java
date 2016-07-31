/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of MapSizeRequirements.
 * <p>
 * @author Gili Tzabari
 */
final class MapSizeRequirementsImpl implements MapSizeRequirements
{
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
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	MapSizeRequirementsImpl(Map<?, ?> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
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
	@Deprecated
	public MapSizeRequirements isNull() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be null", name));
	}

	@Override
	public MapSizeRequirements isNotNull() throws NullPointerException
	{
		// Always true
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d entries. It contained %,d entries.", name, value,
				parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) entries. It contained %,d entries.",
				this.name, name, value, parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d entries. It contained %,d entries.", name, value,
				parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) entries. It contained %,d entries.",
				this.name, name, value, parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d entries. It contained %,d entries.", name, value,
				parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %s (%,d) entries. It contained %,d entries.", this.name,
				name, value, parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d entries. It contained %,d entries.", name, value,
				parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %s (%,d) entries. It contained %,d entries.",
				this.name, name, value, parameter),
			"Actual", map);
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
		throw config.createException(IllegalArgumentException.class,
			String.
				format("%s must contain at least one entry. It contained %,d entries.", name, parameter),
			"Actual", map);
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
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d entries.", name, parameter),
			"Actual", map);
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
		throw new IllegalArgumentException(String.format("%s can never have a negative size", name));
	}

	@Override
	public MapSizeRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" entries. It contained %,d entries.", parameter));
		throw config.createException(IllegalArgumentException.class, message.toString(),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.
				format("%s must contain %,d entries. It contained %,d entries.", name, value, parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) entries. It contained %,d entries.", this.name, name,
				value, parameter),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %,d entries, but did.", name, value),
			"Actual", map);
	}

	@Override
	public MapSizeRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) entries, but did.", this.name, name, value),
			"Actual", map);
	}
}
