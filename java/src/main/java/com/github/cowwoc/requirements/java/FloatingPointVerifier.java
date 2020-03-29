/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleFloatingPointVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of a floating-point number.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <T> the type of the floating-point number
 */
public interface FloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends ExtensibleFloatingPointVerifier<FloatingPointVerifier<T>, T>
{
}
