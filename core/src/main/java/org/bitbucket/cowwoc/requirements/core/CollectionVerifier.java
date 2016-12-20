/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Collection;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ext.ObjectVerifierExtension;

/**
 * Verifies a {@link Collection} parameter.
 *
 * @param <E> the type of elements in the collection
 * @author Gili Tzabari
 */
public interface CollectionVerifier<E>
	extends ObjectVerifierExtension<CollectionVerifier<E>, Collection<E>>,
	Verifier<CollectionVerifier<E>>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	CollectionVerifier<E> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	CollectionVerifier<E> isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain {@code element}
	 */
	CollectionVerifier<E> contains(E element);

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
	CollectionVerifier<E> contains(E element, String name);

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
	CollectionVerifier<E> containsExactly(Collection<E> elements);

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
	CollectionVerifier<E> containsExactly(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value contains any of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code elements}
	 */
	CollectionVerifier<E> containsAny(Collection<E> elements);

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
	CollectionVerifier<E> containsAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value contains all of multiple elements.
	 *
	 * @param elements the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code elements}
	 */
	CollectionVerifier<E> containsAll(Collection<E> elements);

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
	CollectionVerifier<E> containsAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains {@code element}
	 */
	CollectionVerifier<E> doesNotContain(E element);

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
	CollectionVerifier<E> doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain any of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}
	 */
	CollectionVerifier<E> doesNotContainAny(Collection<E> elements);

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
	CollectionVerifier<E> doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of multiple elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}
	 */
	CollectionVerifier<E> doesNotContainAll(Collection<E> elements);

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
	CollectionVerifier<E> doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	CollectionVerifier<E> doesNotContainDuplicates();

	/**
	 * @return a verifier for the collection's size
	 */
	ContainerSizeVerifier size();

	/**
	 * @param consumer verifies the collection's size
	 * @return this
	 */
	CollectionVerifier<E> size(Consumer<ContainerSizeVerifier> consumer);
}
