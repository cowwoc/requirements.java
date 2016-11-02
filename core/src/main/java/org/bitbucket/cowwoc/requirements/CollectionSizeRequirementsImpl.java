/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Default implementation of {@code CollectionSizeRequirements}.
 *
 * @author Gili Tzabari
 */
final class CollectionSizeRequirementsImpl
	implements CollectionSizeRequirements
{
	/**
	 * @param amount an amount
	 * @return "element" or "elements" depending on whether {@code amount} is plural
	 */
	private static String getSingularOrPlural(int amount)
	{
		if (amount == 1)
			return "element";
		return "elements";
	}
	private final SingletonScope scope;
	private final Collection<?> collection;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new CollectionSizeRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	CollectionSizeRequirementsImpl(SingletonScope scope, Collection<?> parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.collection = parameter;
		this.parameter = parameter.size();
		this.name = name + ".size()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param scope      the system configuration
	 * @param collection a collection
	 * @param parameter  the size of the collection
	 * @param name       the name of the parameter
	 * @param config     the instance configuration
	 * @throws AssertionError if {@code scope}, {@code collection}, {@code name} or {@code config}
	 *                        are null; if {@code name} is empty
	 */
	private CollectionSizeRequirementsImpl(SingletonScope scope, Collection<?> collection,
		int parameter, String name, Configuration config)
	{
		assert (collection != null): "collection may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.collection = collection;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	@Override
	public CollectionSizeRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new CollectionSizeRequirementsImpl(scope, collection, parameter, name, newConfig);
	}

	@Override
	public CollectionSizeRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new CollectionSizeRequirementsImpl(scope, collection, parameter, name, newConfig);
	}

	@Override
	public CollectionSizeRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new CollectionSizeRequirementsImpl(scope, collection, parameter, name, newConfig);
	}

	@Override
	@Deprecated
	public CollectionSizeRequirements isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public CollectionSizeRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public CollectionSizeRequirements isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public CollectionSizeRequirements isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isGreaterThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d (%s) %s. It contained %,d %s.", this.name,
				value,
				name, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isLessThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) %s. It contained %,d %s.", this.name, value,
				name, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isNotPositive()
	{
		return isZero();
	}

	@Override
	public CollectionSizeRequirements isPositive()
	{
		if (parameter > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one element. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isNotZero()
	{
		return isPositive();
	}

	@Override
	public CollectionSizeRequirements isZero()
	{
		if (parameter == 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public CollectionSizeRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never have a negative size", name), null);
	}

	@Override
	public CollectionSizeRequirements isIn(Integer first, Integer last)
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] elements. It contained %,d %s.", name, first, last,
				parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.", this.name, name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value, getSingularOrPlural(value))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isNotEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name,
				name, value, getSingularOrPlural(value))).
			addContext("Actual", collection).
			build();
	}

	@Override
	public CollectionSizeRequirements isolate(Consumer<CollectionSizeRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
