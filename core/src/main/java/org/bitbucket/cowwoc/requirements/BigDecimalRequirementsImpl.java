/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code BigDecimalRequirements}.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalRequirementsImpl implements BigDecimalRequirements
{
	private final SingletonScope scope;
	private final BigDecimal parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<BigDecimal> asNumber;

	/**
	 * Creates new BigDecimalRequirementsImpl.
	 * <p>
	 * @param scope     the system configuration
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	BigDecimalRequirementsImpl(SingletonScope scope, BigDecimal parameter, String name,
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
	public BigDecimalRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalRequirementsImpl(scope, parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(Collection<BigDecimal> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalRequirements isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalRequirements isNull()
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public BigDecimalRequirements isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public BigDecimalRequirements isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(BigDecimal first, BigDecimal last)
	{
		asNumber.isIn(first, last);
		return this;
	}

	@Override
	public BigDecimalRequirements isZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() == 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be zero", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public BigDecimalRequirements isNotZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (parameter.signum() != 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public BigDecimalPrecisionRequirements precision()
	{
		return new BigDecimalPrecisionRequirementsImpl(scope, parameter, name, config);
	}

	@Override
	public BigDecimalScaleRequirements scale()
	{
		return new BigDecimalScaleRequirementsImpl(scope, parameter, name, config);
	}

	@Override
	public BigDecimalRequirements isolate(Consumer<BigDecimalRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
