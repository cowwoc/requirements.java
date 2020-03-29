/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Optional;

/**
 * Validates the requirements of an {@link Optional}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 */
public interface OptionalValidator extends ExtensibleObjectValidator<OptionalValidator, Optional<?>>
{
	/**
	 * Ensures that the actual value is present.
	 *
	 * @return the updated validator
	 */
	OptionalValidator isPresent();

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 */
	OptionalValidator isEmpty();

	/**
	 * Ensures that the optional contains a value.
	 *
	 * @param value the expected value; {@code null} matches an empty optional
	 * @return the updated validator
	 */
	OptionalValidator contains(Object value);

	/**
	 * Ensures that the optional contains the expected value.
	 *
	 * @param expected the expected value ({@code null} matches an empty optional)
	 * @param name     the name of the expected value
	 * @return the updated validator
	 */
	OptionalValidator contains(Object expected, String name);
}
