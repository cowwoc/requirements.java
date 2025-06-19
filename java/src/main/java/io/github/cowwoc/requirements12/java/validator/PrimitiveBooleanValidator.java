/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.validator;

import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;
import io.github.cowwoc.requirements12.annotation.CheckReturnValue;

/**
 * Validates the state of a {@code boolean}.
 */
public interface PrimitiveBooleanValidator extends
	ValidatorComponent<PrimitiveBooleanValidator, Boolean>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the value
	 * @throws IllegalStateException if a previous validation failed
	 */
	@CheckReturnValue
	boolean getValue();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value in case of a validation failure
	 * @return the value, or {@code defaultValue} if a previous validation failed
	 */
	@CheckReturnValue
	boolean getValueOrDefault(boolean defaultValue);

	/**
	 * Ensures that the value is {@code true}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is {@code false}
	 */
	PrimitiveBooleanValidator isTrue();

	/**
	 * Ensures that the value is {@code false}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is {@code true}
	 */
	PrimitiveBooleanValidator isFalse();

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 */
	PrimitiveBooleanValidator isEqualTo(boolean expected);

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not equal to {@code expected}</li>
	 *                                  </ul>
	 */
	PrimitiveBooleanValidator isEqualTo(boolean expected, String name);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted}
	 */
	PrimitiveBooleanValidator isNotEqualTo(boolean unwanted);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @param name     the name of the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is equal to {@code unwanted}</li>
	 *                                  </ul>
	 */
	PrimitiveBooleanValidator isNotEqualTo(boolean unwanted, String name);
}