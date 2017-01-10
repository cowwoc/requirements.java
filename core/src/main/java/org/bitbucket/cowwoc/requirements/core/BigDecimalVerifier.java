/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.math.BigDecimal;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ext.NumberVerifierExtension;

/**
 * Verifies a {@link BigDecimal} value.
 *
 * @author Gili Tzabari
 */
public interface BigDecimalVerifier
	extends NumberVerifierExtension<BigDecimalVerifier, BigDecimal>, Verifier<BigDecimalVerifier>
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
	BigDecimalScaleVerifier scale();

	/**
	 * @param consumer verifies the {@code BigDecimal.scale()}
	 * @return this
	 */
	BigDecimalVerifier scale(Consumer<BigDecimalScaleVerifier> consumer);
}
