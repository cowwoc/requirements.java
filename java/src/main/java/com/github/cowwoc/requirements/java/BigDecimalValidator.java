/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleNumberValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Validates the requirements of a {@link BigDecimal} value.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 */
public interface BigDecimalValidator extends ExtensibleNumberValidator<BigDecimalValidator, BigDecimal>
{
	/**
	 * Returns a validator for {@code BigDecimal.precision()}.
	 *
	 * @return a validator for {@code BigDecimal.precision()}
	 */
	BigDecimalPrecisionValidator precision();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@code BigDecimal.precision()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	BigDecimalValidator precision(Consumer<BigDecimalPrecisionValidator> consumer);

	/**
	 * Returns a validator for {@code BigDecimal.scale()}.
	 *
	 * @return a validator for {@code BigDecimal.scale()}
	 */
	PrimitiveNumberValidator<Integer> scale();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@code BigDecimal.scale()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	BigDecimalValidator scale(Consumer<PrimitiveNumberValidator<Integer>> consumer);
}