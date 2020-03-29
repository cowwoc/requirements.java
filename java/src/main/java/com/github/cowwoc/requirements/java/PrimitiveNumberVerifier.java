/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleNumberVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensiblePrimitiveVerifier;

/**
 * Verifies the requirements of a primitive number (e.g. {@link int}).
 *
 * @param <T> the type of the value being verified
 */
public interface PrimitiveNumberVerifier<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveVerifier<PrimitiveNumberVerifier<T>, T>,
	ExtensibleNumberVerifier<PrimitiveNumberVerifier<T>, T>
{
}
