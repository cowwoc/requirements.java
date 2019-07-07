/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleFloatingPointValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

/**
 * Validates the requirements of a primitive floating-point number.
 *
 * @param <T> the type of the floating-point number
 */
public interface PrimitiveFloatingPointValidator<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveValidator<PrimitiveFloatingPointValidator<T>, T>,
	ExtensibleFloatingPointValidator<PrimitiveFloatingPointValidator<T>, T>
{
}
