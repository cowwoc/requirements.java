/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.validator;

import com.github.cowwoc.requirements11.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements11.java.validator.component.DecimalNumberComponent;
import com.github.cowwoc.requirements11.java.validator.component.NegativeNumberComponent;
import com.github.cowwoc.requirements11.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements11.java.validator.component.PositiveNumberComponent;
import com.github.cowwoc.requirements11.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements11.java.validator.component.ZeroNumberComponent;

import java.math.BigDecimal;

/**
 * Validates the state of a {@link BigDecimal}.
 */
public interface BigDecimalValidator extends
	ValidatorComponent<BigDecimalValidator, BigDecimal>,
	ObjectComponent<BigDecimalValidator, BigDecimal>,
	NegativeNumberComponent<BigDecimalValidator>,
	PositiveNumberComponent<BigDecimalValidator>,
	ZeroNumberComponent<BigDecimalValidator>,
	ComparableComponent<BigDecimalValidator, BigDecimal>,
	DecimalNumberComponent<BigDecimalValidator>
{
	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value or {@code factor} are null
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	BigDecimalValidator isMultipleOf(BigDecimal factor);

	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not a multiple of {@code factor}</li>
	 *                                  </ul>
	 */
	BigDecimalValidator isMultipleOf(BigDecimal factor, String name);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value or {@code factor} are null
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	BigDecimalValidator isNotMultipleOf(BigDecimal factor);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @param name   the name of the factor
	 * @return this
	 * @throws NullPointerException     if the value or any of the arguments are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is a multiple of {@code factor}</li>
	 *                                  </ul>
	 */
	BigDecimalValidator isNotMultipleOf(BigDecimal factor, String name);

	/**
	 * Returns a validator for {@code BigDecimal.precision()}.
	 *
	 * @return a validator for {@code BigDecimal.precision()}
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveUnsignedIntegerValidator precision();

	/**
	 * Returns a validator for {@code BigDecimal.scale()}.
	 *
	 * @return a validator for {@code BigDecimal.scale()}
	 * @throws NullPointerException if the value is null
	 */
	PrimitiveIntegerValidator scale();
}