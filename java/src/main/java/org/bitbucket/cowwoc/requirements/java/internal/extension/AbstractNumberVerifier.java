/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.extension;

import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaRequirements;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

/**
 * Default implementation of {@code ExtensibleNumberVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <T> the type of the value
 */
public abstract class AbstractNumberVerifier<S, T extends Number & Comparable<? super T>>
	extends AbstractComparableVerifier<S, T>
	implements ExtensibleNumberVerifier<S, T>
{
	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is
	 *                        empty.
	 */
	protected AbstractNumberVerifier(ApplicationScope scope, String name, T actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public S isNegative()
	{
		if (actual.doubleValue() < 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be negative.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotNegative()
	{
		if (actual.doubleValue() >= 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be negative.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isZero()
	{
		if (actual.doubleValue() == 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be zero.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotZero()
	{
		if (actual.doubleValue() != 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be zero").
			build();
	}

	@Override
	public S isPositive()
	{
		if (actual.doubleValue() > 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be positive.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotPositive()
	{
		if (actual.doubleValue() <= 0L)
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be positive.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isWholeNumber()
	{
		if (isWholeNumber(actual.doubleValue()))
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be a whole number.").
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

		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be a whole number.").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isMultipleOf(T divisor)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		String divisorAsString = config.toString(divisor);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " must be a multiple of " + divisorAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isMultipleOf(T divisor, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble != 0 && isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " must be a multiple of " + name + ".").
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}

	@Override
	public S isNotMultipleOf(T divisor)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble == 0 || !isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		String divisorAsString = config.toString(divisor);
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			name + " may not be a multiple of " + divisorAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public S isNotMultipleOf(T divisor, String name)
	{
		JavaRequirements verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		double divisorAsDouble = divisor.doubleValue();
		if (divisorAsDouble == 0 || isWholeNumber(actual.doubleValue() / divisorAsDouble))
			return getThis();
		throw new ExceptionBuilder<>(scope, config, IllegalArgumentException.class,
			this.name + " may not be a multiple of " + name + ".").
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}
}