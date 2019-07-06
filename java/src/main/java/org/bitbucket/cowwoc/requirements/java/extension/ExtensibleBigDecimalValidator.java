/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.BigDecimalPrecisionValidator;
import org.bitbucket.cowwoc.requirements.java.BigDecimalValidator;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberValidator;

import java.math.BigDecimal;
import java.util.function.Consumer;

/**
 * Validates the requirements of an array of elements but the implementing validator is not guaranteed to be a
 * {@link BigDecimalValidator}.
 *
 * @param <S> the type of validator returned by the methods
 */
public interface ExtensibleBigDecimalValidator<S>
	extends ExtensibleNumberValidator<BigDecimalValidator, BigDecimal>
{
	/**
	 * Returns a validator for {@code BigDecimal.precision()}.
	 *
	 * @return a validator for {@code BigDecimal.precision()}
	 */
	BigDecimalPrecisionValidator precision();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
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
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the {@code BigDecimal.scale()}
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	BigDecimalValidator scale(Consumer<PrimitiveNumberValidator<Integer>> consumer);
}
