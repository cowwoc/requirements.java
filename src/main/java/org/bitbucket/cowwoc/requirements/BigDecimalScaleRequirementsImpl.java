/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Configuration;
import com.google.common.collect.Range;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

/**
 * Default implementation of {@code BigDecimalScaleRequirements}.
 *
 * @author Gili Tzabari
 */
final class BigDecimalScaleRequirementsImpl implements BigDecimalScaleRequirements
{
	private final int parameter;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new BigDecimalScaleRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws NullPointerException     if {@code name} or {@code config} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	BigDecimalScaleRequirementsImpl(BigDecimal parameter, String name,
		Configuration config) throws NullPointerException, IllegalArgumentException
	{
		assert (name != null);
		assert (config != null);
		this.parameter = parameter.scale();
		this.name = name + ".scale()";
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
	private BigDecimalScaleRequirementsImpl(int parameter, String name,
		Configuration config)
	{
		this.parameter = parameter;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(this.parameter, name, config);
	}

	@Override
	public BigDecimalScaleRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalScaleRequirements addContext(String key, Object value)
		throws NullPointerException
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalScaleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public BigDecimalScaleRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	@Deprecated
	public BigDecimalScaleRequirements isNull() throws IllegalArgumentException
	{
		asInt.isNull();
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isIn(Range<Integer> range)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isIn(range);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value) throws IllegalArgumentException
	{
		asInt.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value) throws IllegalArgumentException
	{
		asInt.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isIn(Collection<Integer> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNull() throws NullPointerException
	{
		asInt.isNotNull();
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isZero() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be zero", name));
	}

	@Override
	public BigDecimalScaleRequirements isNotZero() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNotPositive() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be non-positive", name));
	}

	@Override
	public BigDecimalScaleRequirements isPositive() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNegative() throws IllegalArgumentException
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNegative() throws IllegalArgumentException
	{
		throw new IllegalArgumentException(String.format("%s can never be negative", name));
	}

	@Override
	public BigDecimalScaleRequirements isolate(Consumer<BigDecimalScaleRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
