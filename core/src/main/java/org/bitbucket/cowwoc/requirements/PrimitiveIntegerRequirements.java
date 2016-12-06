/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies an {@link int} parameter.
 * <p>
 * @author Gili Tzabari
 */
interface PrimitiveIntegerRequirements
	extends PrimitiveIntegerRequirementsSpi<PrimitiveIntegerRequirements>,
	Isolatable<PrimitiveIntegerRequirements>
{
}
