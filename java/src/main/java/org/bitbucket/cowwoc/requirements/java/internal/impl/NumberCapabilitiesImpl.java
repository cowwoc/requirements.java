/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.capabilities.NumberCapabilities;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Extendable implementation of {@link NumberCapabilities}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public abstract class NumberCapabilitiesImpl<S, T extends Number & Comparable<? super T>>
	extends ComparableCapabilitiesImpl<S, T>
	implements NumberCapabilities<S, T>
{
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected NumberCapabilitiesImpl(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isNegative()
	{
		if (actual.doubleValue() < 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNegative()
	{
		if (actual.doubleValue() >= 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be negative.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isZero()
	{
		if (actual.doubleValue() == 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be zero.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotZero()
	{
		if (actual.doubleValue() != 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
			build();
	}

	@Override
	public S isPositive()
	{
		if (actual.doubleValue() > 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotPositive()
	{
		if (actual.doubleValue() <= 0L)
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be positive.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isWholeNumber()
	{
		if (isWholeNumber(actual.doubleValue()))
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a whole number.", name)).
			addContext("Actual", actual).
			build();
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(double value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1) == 0;
	}

	@Override
	public S isNotWholeNumber()
	{
		// Based on https://stackoverflow.com/a/12748321/14731
		if (!isWholeNumber(actual.doubleValue()))
			return getThis();

		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a whole number.", name)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		scope.getInternalVerifier().requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		String divisorAsString = secretConfiguration.toString(config, divisor);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a multiple of %s.", name, divisorAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a multiple of %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		scope.getInternalVerifier().requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble == 0 || !isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		String divisorAsString = secretConfiguration.toString(config, divisor);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a multiple of %s.", name, divisorAsString)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		verifier.requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble == 0 || isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a multiple of %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}
}
