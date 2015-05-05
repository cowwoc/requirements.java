/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * Verifies preconditions of an {@link int} parameter.
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveIntegerPreconditions<S extends PrimitiveIntegerPreconditions<S>>
	extends NumberPreconditions<S, Integer>
{
}
