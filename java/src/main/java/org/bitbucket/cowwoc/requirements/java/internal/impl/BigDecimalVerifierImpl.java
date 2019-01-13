/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.impl;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.java.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.java.Configuration;
import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.java.internal.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SecretConfiguration;
import org.bitbucket.cowwoc.requirements.java.internal.secrets.SharedSecrets;
import org.bitbucket.cowwoc.requirements.java.internal.util.ExceptionBuilder;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Default implementation of {@link BigDecimalVerifier}.
 */
public final class BigDecimalVerifierImpl
	extends NumberCapabilitiesImpl<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	private final SecretConfiguration secretConfiguration = SharedSecrets.INSTANCE.secretConfiguration;

	/**
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null. If {@code name} is empty.
	 */
	protected BigDecimalVerifierImpl(ApplicationScope scope, String name, BigDecimal actual, Configuration config)
	{
		super(scope, name, actual, config);
	}

	@Override
	public BigDecimalVerifier isZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() == 0)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be zero").
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isNotZero()
	{
		// Number.longValue() truncates the fractional portion, which we need to take into account
		if (actual.signum() != 0)
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be zero").
			build();
	}

	@Override
	public BigDecimalPrecisionVerifier precision()
	{
		return new BigDecimalPrecisionVerifierImpl(scope, name, actual, config);
	}

	@Override
	public BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer)
	{
		consumer.accept(precision());
		return this;
	}

	@Override
	public PrimitiveNumberVerifier<Integer> scale()
	{
		return new BigDecimalScaleVerifierImpl(scope, name, actual, config);
	}

	@Override
	public BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer)
	{
		consumer.accept(scale());
		return this;
	}

	@Override
	public BigDecimalVerifier isWholeNumber()
	{
		if (isWholeNumber(actual))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be a whole number.").
			addContext("Actual", actual).
			build();
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	private static boolean isWholeNumber(BigDecimal value)
	{
		// Based on https://stackoverflow.com/a/12748321/14731
		return value.signum() == 0 || value.stripTrailingZeros().scale() <= 0;
	}

	@Override
	public BigDecimalVerifier isNotWholeNumber()
	{
		// Based on https://stackoverflow.com/a/12748321/14731
		if (!isWholeNumber(actual))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be a whole number.").
			addContext("Actual", actual).
			build();
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	private static boolean isMultipleOf(BigDecimal number, BigDecimal factor)
	{
		return factor.compareTo(BigDecimal.ZERO) != 0 &&
			(number.compareTo(BigDecimal.ZERO) == 0 || number.remainder(factor).compareTo(BigDecimal.ZERO) == 0);
	}

	@Override
	public BigDecimalVerifier isMultipleOf(BigDecimal divisor)
	{
		scope.getInternalVerifier().requireThat(divisor, "divisor").isNotNull();
		if (isMultipleOf(actual, divisor))
			return this;
		String divisorAsString = secretConfiguration.toString(config, divisor);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " must be a multiple of " + divisorAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isMultipleOf(BigDecimal divisor, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			this.name + " must be a multiple of " + name + ".").
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}

	@Override
	public BigDecimalVerifier isNotMultipleOf(BigDecimal divisor)
	{
		scope.getInternalVerifier().requireThat(divisor, "divisor").isNotNull();
		if (!isMultipleOf(actual, divisor))
			return this;
		String divisorAsString = secretConfiguration.toString(config, divisor);
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			name + " may not be a multiple of " + divisorAsString + ".").
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isNotMultipleOf(BigDecimal divisor, String name)
	{
		JavaVerifier verifier = scope.getInternalVerifier();
		verifier.requireThat(divisor, "divisor").isNotNull();
		verifier.requireThat(name, "name").isNotNull().trim().isNotEmpty();
		if (!isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			this.name + " may not be a multiple of " + name + ".").
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}
}
