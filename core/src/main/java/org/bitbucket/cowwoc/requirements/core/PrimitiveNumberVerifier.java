/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import org.bitbucket.cowwoc.requirements.core.capabilities.PrimitiveNumberCapabilities;

/**
 * Verifies a primitive number (e.g. {@link int}).
 *
 * @param <T> the type of the value
 */
@SuppressWarnings("MarkerInterface")
public interface PrimitiveNumberVerifier<T extends Number & Comparable<? super T>>
	extends PrimitiveNumberCapabilities<PrimitiveNumberVerifier<T>, T>
{
}
