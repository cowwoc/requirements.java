/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveIntegerVerifier;

/**
 * Verifies the requirements of a primitive integer number.
 *
 * @param <T> the type of the integer number
 */
public interface PrimitiveIntegerVerifier<T extends Number & Comparable<? super T>>
	extends ExtensibleIntegerVerifier<PrimitiveIntegerVerifier<T>, T>,
	ExtensiblePrimitiveIntegerVerifier<PrimitiveIntegerVerifier<T>, T>
{
}
