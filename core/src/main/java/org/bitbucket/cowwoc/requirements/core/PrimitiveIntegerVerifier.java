/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.IntegerCapabilities;
import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveIntegerCapabilities;

/**
 * Verifies a primitive integer number.
 *
 * @param <T> the type of the integer number
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveIntegerVerifier<T extends Number & Comparable<? super T>>
	extends IntegerCapabilities<PrimitiveIntegerVerifier<T>, T>,
	PrimitiveIntegerCapabilities<PrimitiveIntegerVerifier<T>, T>
{
}
