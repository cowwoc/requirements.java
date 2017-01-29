/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.impl;

import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreUnifiedVerifier;
import org.bitbucket.cowwoc.requirements.core.capabilities.ComparableCapabilities;
import org.bitbucket.cowwoc.requirements.core.capabilities.NumberCapabilities;
import org.bitbucket.cowwoc.requirements.core.scope.ApplicationScope;
import static org.bitbucket.cowwoc.requirements.core.util.ConsoleConstants.LINE_LENGTH;
import org.bitbucket.cowwoc.requirements.core.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link NumberCapabilities}.
 * <p>
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
 */
public abstract class ComparableCapabilitiesImpl<S, T extends Comparable<? super T>>
	extends ObjectCapabilitiesImpl<S, T>
	implements ComparableCapabilities<S, T>
{
	/**
	 * Creates new ComparableCapabilitiesImpl.
	 *
	 * @param scope  the application configuration
	 * @param actual the actual value
	 * @param name   the name of the value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public ComparableCapabilitiesImpl(ApplicationScope scope, T actual, String name,
		Configuration config)
	{
		super(scope, actual, name, config);
	}

	@Override
	public S isLessThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThan(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference < 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isLessThanOrEqualTo(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference <= 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be less than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Maximum", value).
			build();
	}

	@Override
	public S isGreaterThan(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThan(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference > 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value)
	{
		scope.getInternalVerifier().requireThat(value, "value").isNotNull();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", name, value)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isGreaterThanOrEqualTo(T value, String name)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(value, "value").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		int difference = actual.compareTo(value);
		if (difference >= 0)
			return getThis();
		throw new ExceptionBuilder(config, IllegalArgumentException.class,
			String.format("%s must be greater than or equal to %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("Minimum", value).
			build();
	}

	@Override
	public S isBetween(T min, T max)
	{
		CoreUnifiedVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(min, "min").isNotNull();
		verifier.requireThat(max, "max").isNotNull().isGreaterThanOrEqualTo(min, "min");
		if (actual.compareTo(min) >= 0 && actual.compareTo(max) <= 0)
			return getThis();
		StringBuilder message = new StringBuilder(LINE_LENGTH);
		message.append(name).append(" must be in range [").append(min).append(", ").append(max).
			append("]\n").
			append("Actual: ").append(actual);
		throw new ExceptionBuilder(config, IllegalArgumentException.class, message.toString()).
			build();
	}
}
