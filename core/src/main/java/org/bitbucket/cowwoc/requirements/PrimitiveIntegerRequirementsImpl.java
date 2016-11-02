/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Verifies requirements of an {@link int} parameter.
 * <p>
 * @author Gili Tzabari
 */
class PrimitiveIntegerRequirementsImpl implements PrimitiveIntegerRequirements
{
	private final SingletonScope scope;
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<Integer> asNumber;

	/**
	 * Creates new PrimitiveIntegerRequirementsImpl.
	 *
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	protected PrimitiveIntegerRequirementsImpl(SingletonScope scope, int parameter, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asNumber = new NumberRequirementsImpl<>(scope, parameter, name, config);
	}

	@Override
	public PrimitiveIntegerRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new PrimitiveIntegerRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements isEqualTo(Integer value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isEqualTo(Integer value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotEqualTo(Integer value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotEqualTo(Integer value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isIn(Collection<Integer> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isZero()
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotZero()
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThan(Integer value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThan(Integer value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThanOrEqualTo(Integer value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThan(Integer value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThan(Integer value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThanOrEqualTo(Integer value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isIn(Integer first, Integer last)
	{
		asNumber.isIn(first, last);
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerRequirements isNull()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be null", name), null);
	}

	@Override
	public PrimitiveIntegerRequirements isolate(Consumer<PrimitiveIntegerRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
