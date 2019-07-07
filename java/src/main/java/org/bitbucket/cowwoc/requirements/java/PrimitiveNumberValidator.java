/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensiblePrimitiveNumberValidator;

/**
 * Validates the requirements of a primitive number (e.g. {@link int}).
 *
 * @param <T> the type of the value being validated
 */
public interface PrimitiveNumberValidator<T extends Number & Comparable<? super T>>
	extends ExtensiblePrimitiveNumberValidator<PrimitiveNumberValidator<T>, T>
{
}