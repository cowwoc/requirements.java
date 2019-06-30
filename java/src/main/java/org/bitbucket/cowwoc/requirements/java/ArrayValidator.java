/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayValidator;

/**
 * Validates the requirements of an array of elements.
 *
 * @param <E> the Object representation of the array elements
 */
public interface ArrayValidator<E> extends ExtensibleArrayValidator<ArrayValidator<E>, E, E[]>
{
}
