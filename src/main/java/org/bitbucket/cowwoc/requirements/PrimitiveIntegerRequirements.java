/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements;

/**
 * Verifies requirements of an {@link int} parameter.
 * <p>
 * @param <S> the type of requirements that was instantiated
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveIntegerRequirements<S extends PrimitiveIntegerRequirements<S>>
	extends NumberRequirements<S, Integer>
{
}
