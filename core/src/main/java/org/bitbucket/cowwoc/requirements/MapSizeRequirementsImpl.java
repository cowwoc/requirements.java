/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import static org.bitbucket.cowwoc.requirements.util.ConsoleConstants.LINE_LENGTH;
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
	private final SingletonScope scope;
	private final Map<?, ?> map;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new MapSizeRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	MapSizeRequirementsImpl(SingletonScope scope, Map<?, ?> parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.map = parameter;
		this.parameter = parameter.size();
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	@Override
	public MapSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new MapSizeRequirementsImpl(scope, map, name, newConfig);
	}

	@Override
	public MapSizeRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new MapSizeRequirementsImpl(scope, map, name, newConfig);
	}

	@Override
	public MapSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new MapSizeRequirementsImpl(scope, map, name, newConfig);
	}

	@Override
	@Deprecated
	public MapSizeRequirements isNull()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be null", name), null);
	}

	@Override
	public MapSizeRequirements isNotNull()
	{
		// Always true
		return this;
	}

	@Override
	public MapSizeRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public MapSizeRequirements isInstanceOf(Class<?> type)
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
	public MapSizeRequirements isGreaterThan(Integer value)
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
	public MapSizeRequirements isLessThan(Integer value)
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
	public MapSizeRequirements isNotPositive()
	{
		return isZero();
	}

	@Override
	public MapSizeRequirements isPositive()
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
	public MapSizeRequirements isNotZero()
	{
		return isPositive();
	}

	@Override
	public MapSizeRequirements isZero()
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
	public MapSizeRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public MapSizeRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never have a negative size", name), null);
	}

	@Override
	public MapSizeRequirements isIn(Integer first, Integer last)
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		StringBuilder message = new StringBuilder(LINE_LENGTH);
		message.append(name).append(" must contain [").append(first).append(", ").append(last).
			append("]").append(String.format(" entries. It contained %,d %s.", parameter,
			getSingularOrPlural(parameter)));
		throw config.exceptionBuilder(IllegalArgumentException.class, message.toString()).
			addContext("Actual", map).
			build();
	}

	@Override
	public MapSizeRequirements isEqualTo(Integer value)
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
	public MapSizeRequirements isNotEqualTo(Integer value)
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
