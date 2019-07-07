/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleIntegerValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveValidator;

/**
 * Validates the requirements of a primitive integer number.
 *
 * @param <T> the type of the integer number
 */
public interface PrimitiveIntegerValidator<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveValidator<PrimitiveIntegerValidator<T>, T>,
	ExtensibleIntegerValidator<PrimitiveIntegerValidator<T>, T>
{
}
