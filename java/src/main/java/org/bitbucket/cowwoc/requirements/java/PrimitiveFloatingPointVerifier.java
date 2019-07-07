/*
 * Copyright (c) 2017 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of a primitive floating-point number.
 *
 * @param <T> the type of the floating-point number
 */
public interface PrimitiveFloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveVerifier<PrimitiveFloatingPointVerifier<T>, T>,
	ExtensibleFloatingPointVerifier<PrimitiveFloatingPointVerifier<T>, T>
{
}
