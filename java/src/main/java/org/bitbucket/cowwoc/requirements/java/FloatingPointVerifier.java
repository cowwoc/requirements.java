/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.FloatingPointCapabilities;

/**
 * Verifies the requirements of a floating-point number.
 *
 * @param <T> the type of the floating-point number
 */
public interface FloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends FloatingPointCapabilities<FloatingPointVerifier<T>, T>
{
}
