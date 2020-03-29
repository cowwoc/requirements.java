/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of a floating-point number.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <T> the type of the floating-point number
 */
public interface FloatingPointValidator<T extends Number & Comparable<? super T>>
	extends ExtensibleFloatingPointValidator<FloatingPointValidator<T>, T>
{
}