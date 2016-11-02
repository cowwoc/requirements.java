/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of DoubleRequirements.
 *
 * @author Gili Tzabari
 */
final class DoubleRequirementsImpl implements DoubleRequirements
{
	private final SingletonScope scope;
	private final Double parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<Double> asNumber;

	/**
	 * Creates new DoubleRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	DoubleRequirementsImpl(SingletonScope scope, Double parameter, String name, Configuration config)
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
	public DoubleRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new DoubleRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new DoubleRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new DoubleRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public DoubleRequirements isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public DoubleRequirements isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public DoubleRequirements isNotZero()
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public DoubleRequirements isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public DoubleRequirements isZero()
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isIn(Double first, Double last)
	{
		asNumber.isIn(first, last);
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isIn(Collection<Double> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public DoubleRequirements isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public DoubleRequirements isNull()
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public DoubleRequirements isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public DoubleRequirements isNumber()
	{
		if (!parameter.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be a number.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isNotNumber()
	{
		if (parameter.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be a number.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isFinite()
	{
		if (!parameter.isInfinite())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be finite.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isNotFinite()
	{
		if (parameter.isInfinite())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be infinite.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isolate(Consumer<DoubleRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
