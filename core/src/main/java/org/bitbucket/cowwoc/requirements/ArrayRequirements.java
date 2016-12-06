/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.ArrayRequirementsSpi;
import org.bitbucket.cowwoc.requirements.spi.Isolatable;

/**
 * Verifies an array parameter.
 * <p>
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public interface ArrayRequirements<E>
	extends ArrayRequirementsSpi<ArrayRequirements<E>, E>,
	Isolatable<ArrayRequirements<E>>
{
}
