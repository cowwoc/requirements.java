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
 * Default implementation of {@code BigDecimalPrecisionRequirements}.
 * <p>
 * @author Gili Tzabari
 */
final class BigDecimalPrecisionRequirementsImpl implements BigDecimalPrecisionRequirements
{
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new BigDecimalPrecisionRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalPrecisionRequirementsImpl(BigDecimal parameter, String name, Configuration config)
		throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter.precision();
		this.name = name + ".precision()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	/**
	 * Constructor meant to be invoked by {@link #withException(Class)}.
	 *
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 */
	private BigDecimalPrecisionRequirementsImpl(int parameter, String name, Configuration config)
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	@Override
	public BigDecimalPrecisionRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalPrecisionRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalPrecisionRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalPrecisionRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalPrecisionRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalPrecisionRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isGreaterThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isLessThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isIn(range);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		asInt.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotEqualTo(Integer value)
		throws IllegalArgumentException
	{
		asInt.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isIn(Collection<Integer> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionRequirements isNull() throws IllegalArgumentException
	{
		asInt.isNull();
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotNull() throws NullPointerException
	{
		asInt.isNotNull();
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isZero() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalPrecisionRequirements isNotZero() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNotPositive() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalPrecisionRequirements isPositive() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Override
	public BigDecimalPrecisionRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionRequirements isNegative() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be negative", name));
	}

	@Override
	public BigDecimalPrecisionRequirements isolate(Consumer<BigDecimalPrecisionRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
