/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ComparableCapabilities;

/**
 * Verifies the requirements of a {@link Comparable}.
 *
 * @param <T> the type of the value
 */
@SuppressWarnings("MarkerInterface")
public interface ComparableVerifier<T extends Comparable<? super T>>
	extends ComparableCapabilities<ComparableVerifier<T>, T>
{
}
