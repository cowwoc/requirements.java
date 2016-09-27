/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of {@code BigDecimalRequirements}.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalRequirementsImpl implements BigDecimalRequirements
{
	private final BigDecimal parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<BigDecimal> asNumber;

	/**
	 * Creates new BigDecimalRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalRequirementsImpl(BigDecimal parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asNumber = new NumberRequirementsImpl<>(parameter, name, config);
	}

	@Override
	public BigDecimalRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements addContext(String key, Object value) throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value) throws IllegalArgumentException
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isEqualTo(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value) throws IllegalArgumentException
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isNotEqualTo(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(Collection<BigDecimal> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalRequirements isNull() throws IllegalArgumentException
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNull() throws NullPointerException
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public BigDecimalRequirements isNegative() throws IllegalArgumentException
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotNegative() throws IllegalArgumentException
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public BigDecimalRequirements isNotPositive() throws IllegalArgumentException
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public BigDecimalRequirements isPositive() throws IllegalArgumentException
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThan(BigDecimal value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isGreaterThanOrEqualTo(BigDecimal value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThan(BigDecimal value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalRequirements isLessThanOrEqualTo(BigDecimal value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalRequirements isIn(Range<BigDecimal> range)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(range);
		return this;
	}

	@Override
	public BigDecimalRequirements isZero() throws IllegalArgumentException
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
	public BigDecimalRequirements isNotZero() throws IllegalArgumentException
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
		return new BigDecimalPrecisionRequirementsImpl(parameter, name, config);
	}

	@Override
	public BigDecimalScaleRequirements scale()
	{
		return new BigDecimalScaleRequirementsImpl(parameter, name, config);
	}

	@Override
	public BigDecimalRequirements isolate(Consumer<BigDecimalRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
