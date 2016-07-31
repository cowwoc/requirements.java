/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.ComparableRequirementsSpi;

/**
 * Verifies requirements of a {@link Comparable}.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ComparableRequirements<T extends Comparable<? super T>>
	extends ComparableRequirementsSpi<ComparableRequirements<T>, T>, 
	Isolatable<ComparableRequirements<T>>
{
}
