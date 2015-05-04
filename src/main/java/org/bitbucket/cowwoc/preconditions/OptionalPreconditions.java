/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Optional;

/**
 * Verifies preconditions of an {@link Optional} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface OptionalPreconditions extends ObjectPreconditions<OptionalPreconditions, Optional<?>>
{
	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is present
	 */
	OptionalPreconditions isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is present.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is not present
	 */
	OptionalPreconditions isPresent() throws IllegalArgumentException;
}
