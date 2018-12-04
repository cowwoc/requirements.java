/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.NumberCapabilities;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Verifies a {@link BigDecimal} value.
 */
public interface BigDecimalVerifier extends NumberCapabilities<BigDecimalVerifier, BigDecimal>
{
	/**
	 * @return a verifier for {@code BigDecimal.precision()}
	 */
	BigDecimalPrecisionVerifier precision();

	/**
	 * @param consumer verifies the {@code BigDecimal.precision()}
	 * @return this
	 */
	BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer);

	/**
	 * @return a verifier for {@code BigDecimal.scale()}
	 */
	PrimitiveNumberVerifier<Integer> scale();

	/**
	 * @param consumer verifies the {@code BigDecimal.scale()}
	 * @return this
	 */
	BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer);
}
