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
import org.bitbucket.cowwoc.requirements.core.BigDecimalScaleVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * Default implementation of {@code BigDecimalScaleVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalScaleVerifierImpl implements BigDecimalScaleVerifier
{
	private final SingletonScope scope;
	private final BigDecimal bigDecimal;
	private final int actual;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerVerifier asInt;

	/**
	 * Creates new BigDecimalScaleVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public BigDecimalScaleVerifierImpl(SingletonScope scope, BigDecimal actual, String name,
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
		this.asInt = new PrimitiveIntegerVerifierImpl(scope, this.actual, this.name, config);
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
	private BigDecimalScaleVerifierImpl(SingletonScope scope, BigDecimal bigDecimal, int actual,
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
		this.asInt = new PrimitiveIntegerVerifierImpl(scope, this.actual, name, config);
	}

	@Override
	public BigDecimalScaleVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleVerifierImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	public BigDecimalScaleVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new BigDecimalScaleVerifierImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	public BigDecimalScaleVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new BigDecimalScaleVerifierImpl(scope, bigDecimal, actual, name, newConfig);
	}

	@Override
	@Deprecated
	public BigDecimalScaleVerifier isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value, String name)
	{
		asInt.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThan(Integer value)
	{
		asInt.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		asInt.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isGreaterThanOrEqualTo(Integer value)
	{
		asInt.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value, String name)
	{
		asInt.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThan(Integer value)
	{
		asInt.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		asInt.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isLessThanOrEqualTo(Integer value)
	{
		asInt.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isIn(Integer first, Integer last)
	{
		asInt.isIn(first, last);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value)
	{
		asInt.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isEqualTo(Integer value, String name)
	{
		asInt.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value)
	{
		asInt.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotEqualTo(Integer value, String name)
	{
		asInt.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleVerifier isZero()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be zero", name), null);
	}

	@Override
	public BigDecimalScaleVerifier isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleVerifier isNotPositive()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null);
	}

	@Override
	public BigDecimalScaleVerifier isPositive()
	{
		// Always true
		return this;
	}

	@Override
	public BigDecimalScaleVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalScaleVerifier isNegative()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be negative", name), null);
	}

	@Override
	public StringVerifier asString()
	{
		return new StringVerifierImpl(scope, String.valueOf(actual), name, config);
	}

	@Override
	public Optional<Integer> getActualIfPresent()
	{
		return Optional.of(actual);
	}

	@Override
	public Integer getActual()
	{
		return actual;
	}

	@Override
	public BigDecimalScaleVerifier isolate(Consumer<BigDecimalScaleVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
