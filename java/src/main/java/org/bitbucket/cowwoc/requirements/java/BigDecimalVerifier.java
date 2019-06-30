/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.NumberCapabilities;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link BigDecimal} value.
 */
public interface BigDecimalVerifier extends NumberCapabilities<BigDecimalVerifier, BigDecimal>
{
	/**
	 * Returns a verifier for {@code BigDecimal.precision()}.
	 *
	 * @return a verifier for {@code BigDecimal.precision()}
	 */
	BigDecimalPrecisionVerifier precision();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@code BigDecimal.precision()}
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	BigDecimalVerifier precision(Consumer<BigDecimalPrecisionVerifier> consumer);

	/**
	 * Returns a verifier for {@code BigDecimal.scale()}.
	 *
	 * @return a verifier for {@code BigDecimal.scale()}
	 */
	PrimitiveNumberVerifier<Integer> scale();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@code BigDecimal.scale()}
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	BigDecimalVerifier scale(Consumer<PrimitiveNumberVerifier<Integer>> consumer);
}
