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
import org.bitbucket.cowwoc.requirements.util.Exceptions;

/**
 * Default implementation of {@code BigDecimalScaleRequirements}.
 *
 * @author Gili Tzabari
 */
final class BigDecimalScaleRequirementsImpl implements BigDecimalScaleRequirements
{
	private final SingletonScope scope;
	private final BigDecimal bigDecimal;
	private final int actual;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerRequirements asInt;

	/**
	 * Creates new BigDecimalScaleRequirementsImpl.
	 * <p>
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	BigDecimalScaleRequirementsImpl(SingletonScope scope, BigDecimal actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.bigDecimal = actual;
		this.actual = actual.scale();
		this.name = name + ".scale()";
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.actual, this.name, config);
	}

	/**
	 * Internal constructor that assumes that {@code name} has already been updated.
	 *
	 * @param scope      the system configuration
	 * @param bigDecimal the BigDecimal
	 * @param actual     the scale of the BigDecimal
	 * @param name       the name of the parameter
	 * @param config     the instance configuration
	 * @throws AssertionError if {@code scope}, {@code bigDecimal}, {@code name} or {@code config}
	 *                        are null; if {@code name} is empty
	 */
	private BigDecimalScaleRequirementsImpl(SingletonScope scope, BigDecimal bigDecimal, int actual,
		String name, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (bigDecimal != null): "bigDecimal may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.bigDecimal = bigDecimal;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerRequirementsImpl(scope, this.actual, name, config);
	}

	@Override
	public BigDecimalScaleRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleRequirementsImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	public BigDecimalScaleRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalScaleRequirementsImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	public BigDecimalScaleRequirements withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleRequirementsImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	@Deprecated
	public BigDecimalScaleRequirements isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value, String name)
	{
		asInt.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThan(Integer value)
	{
		asInt.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value, String name)
	{
		asInt.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isGreaterThanOrEqualTo(Integer value)
	{
		asInt.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value, String name)
	{
		asInt.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThan(Integer value)
	{
		asInt.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value, String name)
	{
		asInt.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isLessThanOrEqualTo(Integer value)
	{
		asInt.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isIn(Integer first, Integer last)
	{
		asInt.isIn(first, last);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value)
	{
		asInt.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isEqualTo(Integer value, String name)
	{
		asInt.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value)
	{
		asInt.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotEqualTo(Integer value, String name)
	{
		asInt.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isZero()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be zero", name), null);
	}

	@Override
	public BigDecimalScaleRequirements isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNotPositive()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null);
	}

	@Override
	public BigDecimalScaleRequirements isPositive()
	{
		// Always true
		return this;
	}

	@Override
	public BigDecimalScaleRequirements isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleRequirements isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be negative", name), null);
	}

	@Override
	public BigDecimalScaleRequirements isolate(Consumer<BigDecimalScaleRequirements> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
