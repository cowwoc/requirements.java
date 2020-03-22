/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of an array.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ArrayVerifier<A, E> extends ExtensibleArrayVerifier<ArrayVerifier<A, E>, A, E>
{
}
