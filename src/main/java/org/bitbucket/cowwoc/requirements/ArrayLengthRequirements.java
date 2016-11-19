/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies requirements of an array's length.
 * <p>
 * @author Gili Tzabari
 */
public interface ArrayLengthRequirements
	extends PrimitiveIntegerRequirementsSpi<ArrayLengthRequirements>,
	Isolatable<ArrayLengthRequirements>
{
}
