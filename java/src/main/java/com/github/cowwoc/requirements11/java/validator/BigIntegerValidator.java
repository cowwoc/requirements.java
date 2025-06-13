/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.validator;

import com.github.cowwoc.requirements11.java.validator.component.ComparableComponent;
import com.github.cowwoc.requirements11.java.validator.component.NegativeNumberComponent;
import com.github.cowwoc.requirements11.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements11.java.validator.component.PositiveNumberComponent;
import com.github.cowwoc.requirements11.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements11.java.validator.component.ZeroNumberComponent;

import java.math.BigInteger;

/**
 * Validates the state of a {@link BigInteger}.
 */
public interface BigIntegerValidator extends
	ValidatorComponent<BigIntegerValidator, BigInteger>,
	ObjectComponent<BigIntegerValidator, BigInteger>,
	NegativeNumberComponent<BigIntegerValidator>,
	ZeroNumberComponent<BigIntegerValidator>,
	PositiveNumberComponent<BigIntegerValidator>,
	ComparableComponent<BigIntegerValidator, BigInteger>
{
	/**
	 * Ensures that the value is a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value or {@code factor} are null
	 * @throws IllegalArgumentException if the value is not a multiple of {@code factor}
	 */
	BigIntegerValidator isMultipleOf(BigInteger factor);

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
	BigIntegerValidator isMultipleOf(BigInteger factor, String name);

	/**
	 * Ensures that the value is not a multiple of {@code factor}.
	 *
	 * @param factor the number being multiplied
	 * @return this
	 * @throws NullPointerException     if the value or {@code factor} are null
	 * @throws IllegalArgumentException if the value is a multiple of {@code factor}
	 */
	BigIntegerValidator isNotMultipleOf(BigInteger factor);

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
	BigIntegerValidator isNotMultipleOf(BigInteger factor, String name);
}