/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.NumberVerifier;
import org.bitbucket.cowwoc.requirements.core.PrimitiveIntegerVerifier;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;
import org.bitbucket.cowwoc.requirements.core.scope.SingletonScope;
import org.bitbucket.cowwoc.requirements.core.util.Configuration;
import org.bitbucket.cowwoc.requirements.core.util.Exceptions;

/**
 * Verifies an {@link int} parameter.
 * <p>
 * @author Gili Tzabari
 */
public class PrimitiveIntegerVerifierImpl implements PrimitiveIntegerVerifier
{
	private final SingletonScope scope;
	private final int actual;
	private final String name;
	private final Configuration config;
	private final NumberVerifier<Integer> asNumber;

	/**
	 * Creates new PrimitiveIntegerVerifierImpl.
	 *
	 * @param scope  the system configuration
	 * @param actual the actual value of the parameter
	 * @param name   the name of the parameter
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public PrimitiveIntegerVerifierImpl(SingletonScope scope, int actual, String name,
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
	public PrimitiveIntegerVerifier withException(Class<? extends RuntimeException> exception)
	{
		Configuration newConfig = config.withException(exception);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PrimitiveIntegerVerifier addContext(String key, Object value)
	{
		Configuration newConfig = config.addContext(key, value);
		return new PrimitiveIntegerVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PrimitiveIntegerVerifier withContext(List<Entry<String, Object>> context)
	{
		Configuration newConfig = config.withContext(context);
		if (newConfig == config)
			return this;
		return new PrimitiveIntegerVerifierImpl(scope, actual, name, newConfig);
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value)
	{
		asNumber.isEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isEqualTo(Integer value, String name)
	{
		asNumber.isEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value)
	{
		asNumber.isNotEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotEqualTo(Integer value, String name)
	{
		asNumber.isNotEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isIn(Collection<Integer> collection)
	{
		asNumber.isIn(collection);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isInstanceOf(Class<?> type)
	{
		asNumber.isInstanceOf(type);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNull()
	{
		asNumber.isNotNull();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNegative()
	{
		asNumber.isNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotNegative()
	{
		asNumber.isNotNegative();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isZero()
	{
		asNumber.isZero();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotZero()
	{
		asNumber.isNotZero();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isPositive()
	{
		asNumber.isPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isNotPositive()
	{
		asNumber.isNotPositive();
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value, String name)
	{
		asNumber.isGreaterThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThan(Integer value)
	{
		asNumber.isGreaterThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value, String name)
	{
		asNumber.isGreaterThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isGreaterThanOrEqualTo(Integer value)
	{
		asNumber.isGreaterThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value, String name)
	{
		asNumber.isLessThan(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThan(Integer value)
	{
		asNumber.isLessThan(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value, String name)
	{
		asNumber.isLessThanOrEqualTo(value, name);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isLessThanOrEqualTo(Integer value)
	{
		asNumber.isLessThanOrEqualTo(value);
		return this;
	}

	@Override
	public PrimitiveIntegerVerifier isIn(Integer first, Integer last)
	{
		asNumber.isIn(first, last);
		return this;
	}

	@Deprecated
	@Override
	public PrimitiveIntegerVerifier isNull()
	{
		throw Exceptions.createException(IllegalArgumentException.class,
			String.format("%s can never be null", name), null);
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
	public PrimitiveIntegerVerifier isolate(Consumer<PrimitiveIntegerVerifier> consumer)
	{
		consumer.accept(this);
		return this;
	}
}
