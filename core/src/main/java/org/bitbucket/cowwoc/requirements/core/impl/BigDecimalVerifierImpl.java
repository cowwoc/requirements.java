/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalScaleVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;

/**
 * Default implementation of {@code BigDecimalVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalVerifierImpl implements BigDecimalVerifier
{
	private final SingletonScope scope;
	private final BigDecimal actual;
	private final String name;
	private final Configuration config;
	private final NumberVerifier<BigDecimal> asNumber;

	/**
	 * Creates new BigDecimalVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public BigDecimalVerifierImpl(SingletonScope scope, BigDecimal actual, String name,
		Configuration config)
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
	public BigDecimalVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public BigDecimalVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public BigDecimalVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isEqualTo(BigDecimal value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isNotEqualTo(BigDecimal value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isIn(Collection<BigDecimal> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalVerifier isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalVerifier isNull()
	{
		asNumber.isNull();
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public BigDecimalVerifier isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public BigDecimalVerifier isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public BigDecimalVerifier isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public BigDecimalVerifier isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThan(BigDecimal value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isGreaterThanOrEqualTo(BigDecimal value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThan(BigDecimal value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalVerifier isLessThanOrEqualTo(BigDecimal value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalVerifier isBetween(BigDecimal min, BigDecimal max)
	{
		asNumber.isBetween(min, max);
		return this;
	}

	@Override
	public BigDecimalVerifier isZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() == 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s must be zero", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isNotZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() != 0)
			return this;
		throw config.exceptionBuilder(IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return new BigDecimalPrecisionVerifierImpl(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		consumer.accept(precision());
		return this;
	}

	@Override
	public BigDecimalScaleVerifier scale()
	{
		return new BigDecimalScaleVerifierImpl(scope, actual, name, config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<BigDecimalScaleVerifier> consumer)
	{
		consumer.accept(scale());
		return this;
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, actual.toString(), name, config);
	}

	@Override
	public BigDecimalVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
	}

	@Override
	public Optional<BigDecimal> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public BigDecimal getActual()
	{
		return actual;
	}
}
