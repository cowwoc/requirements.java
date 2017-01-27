/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies an {@link Optional}.
 *
 * @author Gili Tzabari
 */
public interface OptionalVerifier
	extends ObjectVerifierExtension<OptionalVerifier, Optional<?>>
{
	/**
	 * Ensures that the actual value is present.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the optional is empty
	 */
	OptionalVerifier isPresent();

	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the optional is present
	 */
	OptionalVerifier isEmpty();

	/**
	 * Ensures that the optional contains a value.
	 *
	 * @param value the expected value; {@code null} matches an empty optional
	 * @return this
	 * @throws IllegalArgumentException if the optional does not contain the specified value
	 */
	OptionalVerifier contains(Object value);

	/**
	 * Ensures that the optional contains the expected value.
	 *
	 * @param expected the expected value; {@code null} matches an empty optional
	 * @param name     the name of the expected value
	 * @return this
	 * @throws IllegalArgumentException if the optional does not contain the specified value
	 */
	OptionalVerifier contains(Object expected, String name);
}
