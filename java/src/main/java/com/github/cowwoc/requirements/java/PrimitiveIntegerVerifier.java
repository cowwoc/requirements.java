/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of a primitive integer number.
 *
 * @param <T> the type of the integer number
 */
public interface PrimitiveIntegerVerifier<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveVerifier<PrimitiveIntegerVerifier<T>, T>,
	ExtensibleIntegerVerifier<PrimitiveIntegerVerifier<T>, T>
{
}
