/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.capabilities.ObjectCapabilities;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a {@link Collection}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface CollectionVerifier<C extends Collection<E>, E>
	extends ObjectCapabilities<CollectionVerifier<C, E>, C>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not empty
	 */
	CollectionVerifier<C, E> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is empty
	 */
	CollectionVerifier<C, E> isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 * @throws IllegalArgumentException if the collection does not contain {@code element}
	 */
	CollectionVerifier<C, E> contains(E element);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element that must exist
	 * @param name     the name of the expected element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection does not contain {@code expected}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> contains(E expected, String name);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more. This method is equivalent to
	 * an {@link #isEqualTo(Object) equality comparison} that ignores element ordering.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection is missing any elements in {@code expected};
	 *                                  if the collection contains elements not found in
	 *                                  {@code expected}
	 */
	CollectionVerifier<C, E> containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more. This method is equivalent to
	 * an {@link #isEqualTo(Object) equality comparison} that ignores element ordering.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection is missing any elements in {@code expected}. If the collection contains elements
	 *                                  not found in {@code expected}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code expected}
	 */
	CollectionVerifier<C, E> containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code expected}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code expected}
	 */
	CollectionVerifier<C, E> containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code expected}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the collection contains {@code element}
	 */
	CollectionVerifier<C, E> doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection contains {@code element}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if the collection contains all of the elements in {@code other}; nothing less, nothing more).
	 */
	CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return this
	 * @throws NullPointerException     if {@code other} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains all of the elements in {@code other}; nothing less, nothing more. If
	 *                                  {@code name} is empty.
	 */
	CollectionVerifier<C, E> doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection contains any of {@code expected}
	 */
	CollectionVerifier<C, E> doesNotContainAny(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection contains all of {@code expected}
	 */
	CollectionVerifier<C, E> doesNotContainAll(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}. If {@code name} is empty.
	 */
	CollectionVerifier<C, E> doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	CollectionVerifier<C, E> doesNotContainDuplicates();

	/**
	 * @return a verifier for the collection's size
	 */
	PrimitiveNumberVerifier<Integer> size();

	/**
	 * @param consumer verifies the collection's size
	 * @return this
	 */
	CollectionVerifier<C, E> size(Consumer<PrimitiveNumberVerifier<Integer>> consumer);

	/**
	 * @param type the array type
	 * @return a verifier for the actual value as an array
	 */
	ArrayVerifier<E> asArray(Class<E> type);

	/**
	 * @param type     the array type
	 * @param consumer verifies the actual value as an array
	 * @return this
	 */
	CollectionVerifier<C, E> asArray(Class<E> type, Consumer<ArrayVerifier<E>> consumer);
}
