/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.impl;

import java.math.BigDecimal;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.BigDecimalPrecisionVerifier;
import org.bitbucket.cowwoc.requirements.core.BigDecimalVerifier;
import org.bitbucket.cowwoc.requirements.core.Configuration;
import org.bitbucket.cowwoc.requirements.core.CoreVerifiers;
import org.bitbucket.cowwoc.requirements.core.PrimitiveNumberVerifier;
import org.bitbucket.cowwoc.requirements.internal.core.scope.ApplicationScope;
import org.bitbucket.cowwoc.requirements.internal.core.util.ExceptionBuilder;

/**
 * Default implementation of {@link BigDecimalVerifier}.
 *
 * @author Gili Tzabari
 */
public final class BigDecimalVerifierImpl
	extends NumberCapabilitiesImpl<BigDecimalVerifier, BigDecimal>
	implements BigDecimalVerifier
{
	/**
	 * Creates new BigDecimalVerifierImpl.
	 *
	 * @param scope  the application configuration
	 * @param name   the name of the value
	 * @param actual the actual value
	 * @param config the instance configuration
	 * @throws AssertionError if {@code scope}, {@code name} or {@code config} are null; if
	 *                        {@code name} is empty
	 */
	public BigDecimalVerifierImpl(ApplicationScope scope, String name, BigDecimal actual,
		Configuration config)
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
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be zero", name)).
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
			String.format("%s must be a whole number.", name)).
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
			String.format("%s may not be a whole number.", name)).
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
			(number.compareTo(BigDecimal.ZERO) == 0 ||
			number.remainder(factor).compareTo(BigDecimal.ZERO) == 0);
	}

	@Override
	public BigDecimalVerifier isMultipleOf(BigDecimal divisor)
	{
		scope.getInternalVerifier().requireThat("divisor", divisor).isNotNull();
		if (isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a multiple of %s.", name, divisor)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isMultipleOf(String name, BigDecimal divisor)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("divisor", divisor).isNotNull();
		if (isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s must be a multiple of %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}

	@Override
	public BigDecimalVerifier isNotMultipleOf(BigDecimal divisor)
	{
		scope.getInternalVerifier().requireThat("divisor", divisor).isNotNull();
		if (!isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a multiple of %s.", name, divisor)).
			addContext("Actual", actual).
			build();
	}

	@Override
	public BigDecimalVerifier isNotMultipleOf(String name, BigDecimal divisor)
	{
		CoreVerifiers verifier = scope.getInternalVerifier();
		verifier.requireThat("name", name).isNotNull().trim().isNotEmpty();
		verifier.requireThat("divisor", divisor).isNotNull();
		if (!isMultipleOf(actual, divisor))
			return this;
		throw new ExceptionBuilder(scope, config, IllegalArgumentException.class,
			String.format("%s may not be a multiple of %s.", this.name, name)).
			addContext("Actual", actual).
			addContext("divisor", divisor).
			build();
	}
}
