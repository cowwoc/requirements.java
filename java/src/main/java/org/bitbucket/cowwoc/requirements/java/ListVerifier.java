/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleCollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

/**
 * Verifies the requirements of a list.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public interface ListVerifier<L, E> extends ExtensibleCollectionVerifier<ListVerifier<L, E>, L, E>
{
}
