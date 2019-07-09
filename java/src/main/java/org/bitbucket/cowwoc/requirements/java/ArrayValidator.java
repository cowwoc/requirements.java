/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayValidator;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

/**
 * Validates the requirements of an array of elements.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <E> the Object representation of the array elements
 */
public interface ArrayValidator<E> extends ExtensibleArrayValidator<ArrayValidator<E>, E, E[]>
{
}
