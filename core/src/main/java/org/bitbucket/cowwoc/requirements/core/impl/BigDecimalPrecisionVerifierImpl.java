/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * Default implementation of {@code BigDecimalPrecisionVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalPrecisionVerifierImpl implements BigDecimalPrecisionVerifier
{
	private final ApplicationScope scope;
	private final int actual;
	private final String name;
	private final Configuration config;
	private final PrimitiveIntegerVerifier asInt;

	/**
	 * Creates new BigDecimalPrecisionVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code name} or {@code config} are null; if {@code name} is empty
	 */
	public BigDecimalPrecisionVerifierImpl(ApplicationScope scope, BigDecimal actual, String name,
		Configuration config)
	{
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual.precision();
		this.name = name + ".precision()";
		this.config = config;
		this.asInt = new PrimitiveIntegerVerifierImpl(scope, this.actual, this.name, config);
	}

	/**
	 * Internal constructor that assumes that {@code name} has already been updated.
	 *
	 * @param scope      the application configuration
	 * @param bigDecimal the BigDecimal
	 * @param actual     the precision of the BigDecimal
	 * @param name       the name of the value
	 * @param config     the instance configuration
	 * @throws AssertionError if {@code scope}, {@code bigDecimal}, {@code name} or {@code config} are
	 *                        null; if {@code name} is empty
	 */
	private BigDecimalPrecisionVerifierImpl(ApplicationScope scope, BigDecimal bigDecimal,
		int actual, String name, Configuration config)
	{
		assert (scope != null): "scope may not be null";
		assert (bigDecimal != null): "bigDecimal may not be null";
		assert (name != null): "name may not be null";
		assert (!name.isEmpty()): "name may not be empty";
		assert (config != null): "config may not be null";
		this.scope = scope;
		this.actual = actual;
		this.name = name;
		this.config = config;
		this.asInt = new PrimitiveIntegerVerifierImpl(scope, this.actual, name, config);
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value, String name)
	{
		asInt.isGreaterThan(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThan(Integer value)
	{
		asInt.isGreaterThan(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		asInt.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isGreaterThanOrEqualTo(Integer value)
	{
		asInt.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value, String name)
	{
		asInt.isLessThan(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThan(Integer value)
	{
		asInt.isLessThan(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		asInt.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isLessThanOrEqualTo(Integer value)
	{
		asInt.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isBetween(Integer min, Integer max)
	{
		asInt.isBetween(min, max);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value)
	{
		asInt.isEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isEqualTo(Integer value, String name)
	{
		asInt.isEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value)
	{
		asInt.isNotEqualTo(value);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotEqualTo(Integer value, String name)
	{
		asInt.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isIn(Collection<Integer> collection)
	{
		asInt.isIn(collection);
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isInstanceOf(Class<?> type)
	{
		asInt.isInstanceOf(type);
		return this;
	}

	@Override
	@Deprecated
	public BigDecimalPrecisionVerifier isNull()
	{
		asInt.isNull();
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNull()
	{
		asInt.isNotNull();
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isZero()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be zero", name), null);
	}

	@Override
	public BigDecimalPrecisionVerifier isNotZero()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNotPositive()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be non-positive", name), null);
	}

	@Override
	public BigDecimalPrecisionVerifier isPositive()
	{
		// Always true
		return this;
	}

	@Override
	public BigDecimalPrecisionVerifier isNotNegative()
	{
		// Always true
		return this;
	}

	@Deprecated
	@Override
	public BigDecimalPrecisionVerifier isNegative()
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
	public BigDecimalPrecisionVerifier asString(Consumer<StringVerifier> consumer)
	{
		consumer.accept(asString());
		return this;
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
	public Configuration configuration()
	{
		return config;
	}

	@Override
	public BigDecimalPrecisionVerifier configuration(Consumer<Configuration> consumer)
	{
		consumer.accept(config);
		return this;
	}
}
