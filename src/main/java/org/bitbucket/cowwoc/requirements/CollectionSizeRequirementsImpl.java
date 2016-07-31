/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code CollectionSizeRequirements}.
 *
 * @author Gili Tzabari
 */
final class CollectionSizeRequirementsImpl
	implements CollectionSizeRequirements
{
	private final Collection<?> collection;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new CollectionSizeRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionSizeRequirementsImpl(Collection<?> parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.collection = parameter;
		this.parameter = parameter.size();
		this.name = name + ".size()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param collection a collection
	 * @param parameter  the size of the collection
	 * @param name       the name of the parameter
	 * @param config     determines the behavior of this verifier
	 */
	private CollectionSizeRequirementsImpl(Collection<?> collection, int parameter, String name,
		Configuration config)
	{
		assert (collection != null);
		assert (name != null);
		assert (config != null);
		this.collection = collection;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	@Override
	public CollectionSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new CollectionSizeRequirementsImpl(collection, parameter, name, newConfig);
	}

	@Override
	public CollectionSizeRequirements withContext(Map<String, Object> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionSizeRequirementsImpl(collection, parameter, name, newConfig);
	}

	@Override
	@Deprecated
	public CollectionSizeRequirements isNull() throws IllegalArgumentException
	{
		asInt.isNull();
		return this;
	}

	@Override
	public CollectionSizeRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotNull() throws NullPointerException
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;

		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.\n" +
				"Actual: %s", name, value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter),
				collection));
	}

	/**
	 * @param amount an amount
	 * @return "element" or "elements" depending on whether {@code amount} is plural
	 */
	private String getSingleOrPlural(int amount)
	{
		if (amount == 1)
			return "element";
		return "elements";
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d %s. It contained %,d %s.", name, value,
				getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s may contain at most %,d (%s) %s. It contained %,d %s.", this.name, value,
				name, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.", name, value,
				getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) %s. It contained %,d %s.", this.name, value,
				name, getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isNotPositive() throws IllegalArgumentException
	{
		return isZero();
	}

	@Override
	public CollectionSizeRequirements isPositive() throws IllegalArgumentException
	{
		if (parameter > 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain at least one element. It contained %,d %s.", name, parameter,
				getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isNotZero() throws IllegalArgumentException
	{
		return isPositive();
	}

	@Override
	public CollectionSizeRequirements isZero() throws IllegalArgumentException
	{
		if (parameter == 0)
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.", name, parameter,
				getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public CollectionSizeRequirements isNegative() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never have a negative size", name));
	}

	@Override
	public CollectionSizeRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		Requirements.requireThat(range, "range").isNotNull();
		if (range.contains(parameter))
			return this;
		StringBuilder message = new StringBuilder(name + " must contain " + range);
		message.append(String.format(" elements. It contained %,d %s.\n" +
			"Actual: %s", parameter, getSingleOrPlural(parameter), collection));
		throw config.createException(IllegalArgumentException.class,
			message.toString());
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value, getSingleOrPlural(
				value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.", this.name, name, value,
				getSingleOrPlural(value), parameter, getSingleOrPlural(parameter)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value, getSingleOrPlural(value)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value, String name)
		throws IllegalArgumentException
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.createException(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name,
				name, value, getSingleOrPlural(value)),
			"Actual", collection);
	}

	@Override
	public CollectionSizeRequirements isolate(Consumer<CollectionSizeRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
