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
 * Default implementation of {@code ArrayLengthRequirements}.
 *
 * @author Gili Tzabari
 */
final class ArrayLengthRequirementsImpl
	implements ArrayLengthRequirements
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
	private final Object[] array;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new ArrayLengthRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	ArrayLengthRequirementsImpl(SingletonScope scope, Object[] parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.array = parameter;
		this.parameter = parameter.length;
		this.name = name + ".length";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param scope     the system configuration
	 * @param array     an array
	 * @param parameter the length of the array
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code array}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	private ArrayLengthRequirementsImpl(SingletonScope scope, Object[] array, int parameter,
		String name, Configuration config)
	{
		assert (array != null): "array may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.array = array;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.parameter, name, config);
	}

	@Override
	public ArrayLengthRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new ArrayLengthRequirementsImpl(scope, array, parameter, name, newConfig);
	}

	@Override
	public ArrayLengthRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new ArrayLengthRequirementsImpl(scope, array, parameter, name, newConfig);
	}

	@Override
	public ArrayLengthRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new ArrayLengthRequirementsImpl(scope, array, parameter, name, newConfig);
	}

	@Override
	@Deprecated
	public ArrayLengthRequirements isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public ArrayLengthRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public ArrayLengthRequirements isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public ArrayLengthRequirements isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Override
	public ArrayLengthRequirements isGreaterThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter >= value)
			return this;

		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter >= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isGreaterThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isGreaterThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter > value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain more than %s (%,d) %s. It contained %,d %s.", this.name, name,
				value, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isLessThanOrEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter <= value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not contain more than %,d (%s) %s. It contained %,d %s.", this.name,
				value, name, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isLessThan(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isLessThan(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (parameter < value)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain less than %,d (%s) %s. It contained %,d %s.", this.name, value,
				name, getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isNotPositive()
	{
		return isZero();
	}

	@Override
	public ArrayLengthRequirements isPositive()
	{
		if (parameter > 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain at least one element. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isNotZero()
	{
		return isPositive();
	}

	@Override
	public ArrayLengthRequirements isZero()
	{
		if (parameter == 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be empty. It contained %,d %s.", name, parameter,
				getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public ArrayLengthRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never have a negative size", name), null);
	}

	@Override
	public ArrayLengthRequirements isIn(Integer first, Integer last)
	{
		Requirements.requireThat(first, "first").isNotNull();
		Requirements.requireThat(last, "last").isNotNull().isGreaterThanOrEqualTo(first, "first");
		if (parameter >= first && parameter <= last)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain [%d, %d] elements. It contained %,d %s.", name, first, last,
				parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %,d %s. It contained %,d %s.", name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must contain %s (%,d) %s. It contained %,d %s.", this.name, name, value,
				getSingularOrPlural(value), parameter, getSingularOrPlural(parameter))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isNotEqualTo(Integer value)
	{
		Requirements.requireThat(value, "value").isNotNull();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %,d %s, but did.", name, value, getSingularOrPlural(value))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isNotEqualTo(Integer value, String name)
	{
		Requirements.requireThat(value, "value").isNotNull();
		Requirements.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!Objects.equals(parameter, value))
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must not contain %s (%,d) %s, but did.", this.name,
				name, value, getSingularOrPlural(value))).
			addContext("Actual", array).
			build();
	}

	@Override
	public ArrayLengthRequirements isolate(Consumer<ArrayLengthRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
