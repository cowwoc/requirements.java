/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core;

import java.util.Collection;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.capabilities.ObjectCapabilities;

/**
 * Verifies an array.
 *
 * @param <E> the type of elements in the array
 * @author Gili Tzabari
 */
public interface ArrayVerifier<E> extends ObjectCapabilities<ArrayVerifier<E>, E[]>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array is not empty
	 */
	ArrayVerifier<E> isEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array is empty
	 */
	ArrayVerifier<E> isNotEmpty() throws IllegalArgumentException;

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	ArrayVerifier<E> contains(E expected) throws IllegalArgumentException;

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param name     the name of the element
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain
	 *                                  {@code expected}
	 *
	 */
	ArrayVerifier<E> contains(String name, E expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array is missing any element found in {@code expected};
	 *                                  if the array contains any element not found in
	 *                                  {@code expected}
	 */
	ArrayVerifier<E> containsExactly(Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array is missing any element
	 *                                  found in {@code expected}; if the array contains any element
	 *                                  not found in {@code expected}
	 */
	ArrayVerifier<E> containsExactly(String name, Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain any of {@code expected}
	 */
	ArrayVerifier<E> containsAny(Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain any
	 *                                  of {@code expected}
	 *
	 */
	ArrayVerifier<E> containsAny(String name, Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain all of {@code expected}
	 */
	ArrayVerifier<E> containsAll(Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain all
	 *                                  of {@code expected}
	 */
	ArrayVerifier<E> containsAll(String name, Collection<E> expected)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the array contains {@code element}
	 */
	ArrayVerifier<E> doesNotContain(E element) throws IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param name    the name of the element
	 * @param element the element that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains
	 *                                  {@code element}
	 */
	ArrayVerifier<E> doesNotContain(String name, E element)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain exactly the specified elements (nothing more,
	 * nothing less).
	 *
	 * @param other the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if the array contains all of the elements in {@code other}
	 *                                  (nothing more, nothing less).
	 */
	ArrayVerifier<E> doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements (nothing more,
	 * nothing less).
	 *
	 * @param name  the name of the collection
	 * @param other the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code other} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains all of
	 *                                  the elements in {@code other} (nothing more, nothing less).
	 */
	ArrayVerifier<E> doesNotContainExactly(String name, Collection<E> other);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAny(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code elements} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains any of
	 *                                  {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAny(String name, Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAll(Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain all of specified elements.
	 *
	 * @param name     the name of the elements
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code elements} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains all of
	 *                                  {@code elements}
	 */
	ArrayVerifier<E> doesNotContainAll(String name, Collection<E> elements)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	ArrayVerifier<E> doesNotContainDuplicates()
		throws IllegalArgumentException;

	/**
	 * @return a verifier for the array's length
	 */
	PrimitiveNumberVerifier<Integer> length();

	/**
	 * @param consumer verifies the array's length
	 * @return this
	 */
	ArrayVerifier<E> length(Consumer<PrimitiveNumberVerifier<Integer>> consumer);

	/**
	 * @return a verifier for the actual value as a collection
	 */
	CollectionVerifier<Collection<E>, E> asCollection();

	/**
	 * @param consumer verifies the actual value as a collection
	 * @return this
	 */
	ArrayVerifier<E> asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer);
}
