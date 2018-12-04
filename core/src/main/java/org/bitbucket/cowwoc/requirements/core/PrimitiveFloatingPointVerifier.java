/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.FloatingPointCapabilities;
import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveNumberCapabilities;

/**
 * Verifies a primitive floating-point number.
 *
 * @param <T> the type of the floating-point number
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveFloatingPointVerifier<T extends Number & Comparable<? super T>>
	extends FloatingPointCapabilities<PrimitiveFloatingPointVerifier<T>, T>,
	PrimitiveNumberCapabilities<PrimitiveFloatingPointVerifier<T>, T>
{
}
