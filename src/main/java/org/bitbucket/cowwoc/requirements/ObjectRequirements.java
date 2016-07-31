/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

import org.bitbucket.cowwoc.requirements.spi.ObjectRequirementsSpi;

/**
 * Verifies requirements of an {@link Object} parameter.
 * <p>
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectRequirements<T>
	extends ObjectRequirementsSpi<ObjectRequirements<T>, T>, Isolatable<ObjectRequirements<T>>
{
}