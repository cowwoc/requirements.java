/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import com.google.common.collect.Range;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.spi.Configuration;

/**
 * Default implementation of DoubleRequirements.
 *
 * @author Gili Tzabari
 */
public final class DoubleRequirementsImpl implements DoubleRequirements
{
	private final Double parameter;
	private final String name;
	private final Configuration config;
	private final NumberRequirements<Double> asNumber;

	/**
	 * Creates new DoubleRequirementsImpl.
	 * <p>
	 * @param parameter the value of the parameter
	 * @param name      the name of the parameter
	 * @param config    determines the behavior of this verifier
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	DoubleRequirementsImpl(Double parameter, String name, Configuration config)
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
	public DoubleRequirements withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new DoubleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new DoubleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements withContext(List<Entry<String, Object>> context)
		throws NullPointerException
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new DoubleRequirementsImpl(parameter, name, newConfig);
	}

	@Override
	public DoubleRequirements isNegative() throws IllegalArgumentException
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public DoubleRequirements isNotNegative() throws IllegalArgumentException
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public DoubleRequirements isNotPositive() throws IllegalArgumentException
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public DoubleRequirements isNotZero() throws IllegalArgumentException
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public DoubleRequirements isPositive() throws IllegalArgumentException
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public DoubleRequirements isZero() throws IllegalArgumentException
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThan(Double value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isGreaterThanOrEqualTo(Double value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isLessThan(Double value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isLessThanOrEqualTo(Double value)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isIn(Range<Double> range)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(range);
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value) throws IllegalArgumentException
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isEqualTo(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value) throws IllegalArgumentException
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public DoubleRequirements isNotEqualTo(Double value, String name)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleRequirements isIn(Collection<Double> collection)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public DoubleRequirements isInstanceOf(Class<?> type)
		throws NullPointerException, IllegalArgumentException
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public DoubleRequirements isNull() throws IllegalArgumentException
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public DoubleRequirements isNotNull() throws NullPointerException
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public DoubleRequirements isNumber() throws IllegalArgumentException
	{
		if (!parameter.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be a number.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isNotNumber() throws IllegalArgumentException
	{
		if (parameter.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be a number.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isFinite() throws IllegalArgumentException
	{
		if (!parameter.isInfinite())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be finite.", name)).
			addContext("Actual", parameter).
			build();
	}

	@Override
	public DoubleRequirements isNotFinite() throws IllegalArgumentException
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
