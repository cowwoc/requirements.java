/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayVerifier;

/**
 * Verifies the requirements of an array.
 *
 * @param <E> the type of elements in the array
 */
public interface ArrayVerifier<E> extends ExtensibleArrayVerifier<ArrayVerifier<E>, E, E[]>
{
}
