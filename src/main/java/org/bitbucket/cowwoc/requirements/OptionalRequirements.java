/*
 * Copyright 2014 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import java.util.Optional;
import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Verifies requirements of an {@link Optional} parameter.
 * <p>
 * @author Gili Tzabari
 */
public interface OptionalRequirements
	extends ObjectRequirementsSpi<OptionalRequirements, Optional<?>>,
	Isolatable<OptionalRequirements>
{
	/**
	 * Ensures that the parameter is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is present
	 */
	OptionalRequirements isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is present.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is not present
	 */
	OptionalRequirements isPresent() throws IllegalArgumentException;
}
