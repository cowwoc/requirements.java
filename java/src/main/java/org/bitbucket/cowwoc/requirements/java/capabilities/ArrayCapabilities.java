/*
 * Copyright 2018 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.capabilities;

import org.bitbucket.cowwoc.requirements.java.ArrayVerifier;
import org.bitbucket.cowwoc.requirements.java.CollectionVerifier;
import org.bitbucket.cowwoc.requirements.java.PrimitiveNumberVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Verifies the requirements of an array of elements but the implementing verifier is not guaranteed to be a
 * {@link ArrayVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <E> the Object representation of the array elements
 * @param <R> the type of the array
 */
public interface ArrayCapabilities<S, E, R>
	extends ObjectCapabilities<S, R>
{
	/**
	 * Ensures that the array is empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the array is not empty.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param expected the element
	 * @return this
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	S contains(E expected);

	/**
	 * Ensures that the array contains an element.
	 *
	 * @param name     the name of the element
	 * @param expected the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain
	 *                                  {@code expected}
	 */
	S contains(String name, E expected);

	/**
	 * Ensures that the array contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array is missing any element found in {@code expected};
	 *                                  if the array contains any element not found in
	 *                                  {@code expected}
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the array contains the specified elements; nothing less, nothing more.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array is missing any element
	 *                                  found in {@code expected}; if the array contains any element
	 *                                  not found in {@code expected}
	 */
	S containsExactly(String name, Collection<E> expected);

	/**
	 * Ensures that the array contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain any of {@code expected}
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the array contains any of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain any
	 *                                  of {@code expected}
	 */
	S containsAny(String name, Collection<E> expected);

	/**
	 * Ensures that the array contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain all of {@code expected}
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Ensures that the array contains all of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param expected the elements that must exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code expected} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array does not contain all
	 *                                  of {@code expected}
	 */
	S containsAll(String name, Collection<E> expected);

	/**
	 * Ensures that the array does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 * @throws IllegalArgumentException if the array contains {@code element}
	 */
	S doesNotContain(E element);

	/**
	 * Ensures that the array does not contain an element.
	 *
	 * @param name    the name of the element
	 * @param element the element that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains
	 *                                  {@code element}
	 */
	S doesNotContain(String name, E element);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing more,
	 * nothing less.
	 *
	 * @param other the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if the collection contains all of the elements in
	 *                                  {@code other}; nothing more, nothing less.
	 */
	S doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing more,
	 * nothing less.
	 *
	 * @param name  the name of the element
	 * @param other the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code other} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the collection contains all of
	 *                                  the elements in {@code other}; nothing more, nothing less.
	 */
	S doesNotContainExactly(String name, Collection<E> other);

	/**
	 * Ensures that the array does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}
	 */
	S doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the array does not contain any of the specified elements.
	 *
	 * @param name     the name of the elements
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code elements} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains any of
	 *                                  {@code elements}
	 */
	S doesNotContainAny(String name, Collection<E> elements);

	/**
	 * Ensures that the array does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}
	 */
	S doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the array does not contain all of specified elements.
	 *
	 * @param name     the name of the elements
	 * @param elements the elements that must not exist
	 * @return this
	 * @throws NullPointerException     if {@code name} or {@code elements} are null
	 * @throws IllegalArgumentException if {@code name} is empty; if the array contains all of
	 *                                  {@code elements}
	 */
	S doesNotContainAll(String name, Collection<E> elements);

	/**
	 * Ensures that the array does not contain any duplicate elements.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * @return a verifier for the array's length
	 */
	PrimitiveNumberVerifier<Integer> length();

	/**
	 * @param consumer verifies the array's length
	 * @return this
	 */
	S length(Consumer<PrimitiveNumberVerifier<Integer>> consumer);

	/**
	 * @return a verifier for the actual value as a collection
	 */
	CollectionVerifier<Collection<E>, E> asCollection();

	/**
	 * @param consumer verifies the actual value as a collection
	 * @return this
	 */
	S asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer);
}
