/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies an {@link Optional} parameter.
 *
 * @author Gili Tzabari
 */
public interface OptionalVerifier
	extends ObjectVerifierExtension<OptionalVerifier, Optional<?>>, Verifier<OptionalVerifier>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is present
	 */
	OptionalVerifier isEmpty();

	/**
	 * Ensures that the actual value is present.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is not present
	 */
	OptionalVerifier isPresent();
}
