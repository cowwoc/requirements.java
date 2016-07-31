/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;

/**
 * Verifies requirements of an {@link Optional} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface OptionalRequirements extends ObjectRequirements<OptionalRequirements, Optional<?>>
{
	/**
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is present
	 */
	OptionalRequirements isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is present.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the value is not present
	 */
	OptionalRequirements isPresent() throws IllegalArgumentException;
}
