/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.NumberRequirementsSpi;

/**
 * Requirements for a {@code Number}.
 *
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface NumberRequirements<T extends Number & Comparable<? super T>>
	extends NumberRequirementsSpi<NumberRequirements<T>, T>, Isolatable<NumberRequirements<T>>
{
}
