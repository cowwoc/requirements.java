/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Verifies requirements of an {@link int} parameter.
 * <p>
 * @author Gili Tzabari
 */
class PrimitiveIntegerRequirementsImpl implements PrimitiveIntegerRequirements
{
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<Integer> asNumber;

	/**
	 * Creates new PrimitiveIntegerRequirementsImpl.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	protected PrimitiveIntegerRequirementsImpl(int parameter, String name, Configuration config)
		throws AssertionError
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asNumber = new NumberRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public PrimitiveIntegerRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new PrimitiveIntegerRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public PrimitiveIntegerRequirements isEqualTo(Integer value)
		throws IllegalArgumentException
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotEqualTo(Integer value)
		throws IllegalArgumentException
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isIn(Collection<Integer> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotNull() throws NullPointerException
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNegative() throws IllegalArgumentException
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotNegative() throws IllegalArgumentException
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isZero() throws IllegalArgumentException
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotZero() throws IllegalArgumentException
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isPositive() throws IllegalArgumentException
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isNotPositive() throws IllegalArgumentException
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isGreaterThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isLessThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerRequirements isIn(Integer first, Integer last)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(first, last);
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerRequirements isNull() throws IllegalArgumentException
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
