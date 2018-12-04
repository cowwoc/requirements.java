/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.IntegerCapabilities;

/**
 * Verifies the requirements of an integer number.
 *
 * @param <T> the type of the integer number
 */
@SuppressWarnings("MarkerInterface")
public interface IntegerVerifier<T extends Number & Comparable<? super T>>
	extends IntegerCapabilities<IntegerVerifier<T>, T>
{
}
