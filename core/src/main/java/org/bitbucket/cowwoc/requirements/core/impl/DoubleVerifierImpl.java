/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.DoubleVerifier;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;

/**
 * Default implementation of DoubleVerifier.
 *
 * @author Gili Tzabari
 */
public final class DoubleVerifierImpl implements DoubleVerifier
{
	private final SingletonScope scope;
	private final Double actual;
	private final String name;
	private final Configuration config;
	private final NumberVerifier<Double> asNumber;

	/**
	 * Creates new DoubleVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public DoubleVerifierImpl(SingletonScope scope, Double actual, String name, Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asNumber = new NumberVerifierImpl<>(scope, actual, name, config);
	}

	@Override
	public DoubleVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new DoubleVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public DoubleVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new DoubleVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public DoubleVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new DoubleVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public DoubleVerifier isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public DoubleVerifier isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public DoubleVerifier isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public DoubleVerifier isNotZero()
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public DoubleVerifier isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public DoubleVerifier isZero()
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThan(Double value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isGreaterThanOrEqualTo(Double value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isLessThan(Double value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isLessThanOrEqualTo(Double value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public DoubleVerifier isBetween(Double min, Double max)
	{
		asNumber.isBetween(min, max);
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public DoubleVerifier isEqualTo(Double value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public DoubleVerifier isNotEqualTo(Double value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public DoubleVerifier isIn(Collection<Double> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public DoubleVerifier isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public DoubleVerifier isNull()
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public DoubleVerifier isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public DoubleVerifier isNumber()
	{
		if (!actual.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isNotNumber()
	{
		if (actual.isNaN())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be a number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isFinite()
	{
		if (!actual.isInfinite())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be finite.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public DoubleVerifier isNotFinite()
	{
		if (actual.isInfinite())
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be infinite.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public DoubleVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<Double> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public Double getActual()
	{
		return actual;
	}
}
