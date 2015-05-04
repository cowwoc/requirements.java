/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import java.util.Collection;

/**
 * Verifies preconditions of a {@link Collection} parameter.
 * <p>
 * @param <E> the type of element in the collection
 * @author Gili Tzabari
 */
public interface CollectionPreconditions<E>
	extends ObjectPreconditions<CollectionPreconditions<E>, Collection<E>>
{
	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	CollectionPreconditions<E> isNotEmpty() throws IllegalArgumentException;
}
