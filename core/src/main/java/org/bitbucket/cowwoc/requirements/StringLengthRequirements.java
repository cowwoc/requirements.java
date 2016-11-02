/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.Isolatable;
import org.bitbucket.cowwoc.requirements.spi.PrimitiveIntegerRequirementsSpi;

/**
 * Verifies requirements of a {@link String#length()}.
 * <p>
 * @author Gili Tzabari
 */
public interface StringLengthRequirements
	extends PrimitiveIntegerRequirementsSpi<StringLengthRequirements>, 
	Isolatable<StringLengthRequirements>
{
}
