/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator;

import com.github.cowwoc.requirements10.java.validator.component.ObjectComponent;
import com.github.cowwoc.requirements10.java.validator.component.ValidatorComponent;

import java.util.Optional;

/**
 * Validates the state of an {@link Optional}.
 *
 * @param <T> the type of object in the optional
 */
public interface OptionalValidator<T> extends
	ValidatorComponent<OptionalValidator<T>, Optional<T>>,
	ObjectComponent<OptionalValidator<T>, Optional<T>>
{
	/**
	 * Ensures that the optional contains a value.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional is empty
	 */
	OptionalValidator<T> isPresent();

	/**
	 * Ensures that the optional is absent.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional contains a value
	 */
	OptionalValidator<T> isEmpty();

	/**
	 * Ensures that the optional contains a value.
	 *
	 * @param expected the expected value; {@code null} matches an empty optional
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the optional does not contain {@code expected}
	 */
	OptionalValidator<T> contains(Object expected);

	/**
	 * Ensures that the optional contains {@code expected}.
	 *
	 * @param expected the expected value ({@code null} matches an empty optional)
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if the value or {@code name} are null
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the optional does not contain {@code expected}</li>
	 *                                  </ul>
	 */
	OptionalValidator<T> contains(Object expected, String name);
}