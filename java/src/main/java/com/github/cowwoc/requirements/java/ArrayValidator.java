/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleArrayValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of an array of elements.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ArrayValidator<A, E> extends ExtensibleArrayValidator<ArrayValidator<A, E>, A, E>
{
}
