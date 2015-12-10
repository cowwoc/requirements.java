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
	 * Ensures that the parameter is empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not empty
	 */
	CollectionPreconditions<E, T> isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not empty.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is empty
	 */
	CollectionPreconditions<E, T> isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains an element.
	 * <p>
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain the element
	 */
	CollectionPreconditions<E, T> contains(E element) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains any of multiple elements.
	 * <p>
	 * @param elements the elements that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain any of the elements
	 */
	CollectionPreconditions<E, T> containsAny(Collection<E> elements) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter contains all of multiple elements.
	 * <p>
	 * @param elements the elements that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain all of the elements
	 */
	CollectionPreconditions<E, T> containsAll(Collection<E> elements) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter does not contain an element.
	 * <p>
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains the element
	 */
	CollectionPreconditions<E, T> doesNotContain(E element) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter does not contain any of multiple elements.
	 * <p>
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains any of the elements
	 */
	CollectionPreconditions<E, T> doesNotContainAny(Collection<E> elements)
		throws IllegalArgumentException;

	/**
	 * Ensures that the parameter does not contain all of multiple elements.
	 * <p>
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains all of the elements
	 */
	CollectionPreconditions<E, T> doesNotContainAll(Collection<E> elements)
		throws IllegalArgumentException;

	/**
	 * @return preconditions over Collection.size()
	 */
	CollectionSizePreconditions size();
}
