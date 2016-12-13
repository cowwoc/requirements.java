/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import org.bitbucket.cowwoc.requirements.core.ContainerSizeVerifier;

/**
 * Verifies a {@link Collection} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <E> the type of element in the collection
 * @author Gili Tzabari
 */
public interface CollectionVerifierSpi<S extends CollectionVerifierSpi<S, E>, E>
	extends ObjectVerifierSpi<S, Collection<E>>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain {@code element}
	 */
	S contains(E element);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection does not contain {@code element}; if
	 *                                  {@code name} is empty
	 */
	S contains(E element, String name);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection is missing any elements in {@code elements};
	 *                                  if the collection contains elements not found in
	 *                                  {@code elements}
	 */
	S containsExactly(Collection<E> elements);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection is missing any elements in {@code elements};
	 *                                  if the collection contains elements not found in
	 *                                  {@code elements}; if {@code name} is empty
	 */
	S containsExactly(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code elements}
	 */
	S containsAny(Collection<E> elements);

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S containsAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code elements}
	 */
	S containsAll(Collection<E> elements);

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S containsAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains {@code element}
	 */
	S doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection contains {@code element}; if {@code name} is
	 *                                  empty
	 */
	S doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}
	 */
	S doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}
	 */
	S doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}; if
	 *                                  {@code name} is empty
	 */
	S doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * @return verifier for {@code Collection.size()}
	 */
	ContainerSizeVerifier size();
}