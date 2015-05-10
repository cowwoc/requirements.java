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
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface CollectionPreconditions<E, T extends Collection<E>>
	extends ObjectPreconditions<CollectionPreconditions<E, T>, T>
{
	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	CollectionPreconditions<E, T> isNotEmpty() throws IllegalArgumentException;

	/**
	 * @return preconditions over Collection.size()
	 */
	CollectionSizePreconditions size();
}
