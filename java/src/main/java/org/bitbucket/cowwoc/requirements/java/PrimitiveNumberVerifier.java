/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.PrimitiveNumberCapabilities;

/**
 * Verifies the requirements of a primitive number (e.g. {@link int}).
 *
 * @param <T> the type of the value
 */
public interface PrimitiveNumberVerifier<T extends Number & Comparable<? super T>> extends
	PrimitiveNumberCapabilities<PrimitiveNumberVerifier<T>, T>
{
}
