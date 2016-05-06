/*
 * Copyright 2016 Gili.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

/**
 * Verifies preconditions of a {@link Comparable}.
 * <p>
 * @param <T> the type of objects that the parameter may be compared to
 * @author Gili Tzabari
 */
@SuppressWarnings("MarkerInterface")
public interface ComparablePreconditions<T extends Comparable<? super T>>
	extends CompareToPreconditions<ComparablePreconditions<T>, T>
{
}
