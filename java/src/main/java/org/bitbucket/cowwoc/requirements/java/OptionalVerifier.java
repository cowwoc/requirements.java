/*
 * Copyright (c) 2014 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.util.Optional;

/**
 * Verifies the requirements of an {@link Optional}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 */
public interface OptionalVerifier extends ExtensibleObjectVerifier<OptionalVerifier, Optional<?>>
{
	/**
	 * Ensures that the actual value is present.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the optional is empty
	 */
	OptionalVerifier isPresent();

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the optional is present
	 */
	OptionalVerifier isEmpty();

	/**
	 * Ensures that the optional contains a value.
	 *
	 * @param value the expected value; {@code null} matches an empty optional
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the optional does not contain the specified value
	 */
	OptionalVerifier contains(Object value);

	/**
	 * Ensures that the optional contains the expected value.
	 *
	 * @param expected the expected value ({@code null} matches an empty optional)
	 * @param name     the name of the expected value
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the optional does not contain the specified value
	 */
	OptionalVerifier contains(Object expected, String name);
}
