/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Verifies the requirements of an array.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ArrayVerifier<A, E> extends ExtensibleObjectVerifier<ArrayVerifier<A, E>, A>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the array is not empty
	 */
	ArrayVerifier<A, E> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the array is empty
	 */
	ArrayVerifier<A, E> isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the array does not contain {@code expected}
	 */
	ArrayVerifier<A, E> contains(E expected);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the array does not contain {@code expected}. If {@code name} is
	 *                                  blank.
	 */
	ArrayVerifier<A, E> contains(E expected, String name);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array is missing any element found in {@code expected};
	 *                                  if the array contains any element not found in {@code expected}
	 */
	ArrayVerifier<A, E> containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the array is missing any element found in {@code expected}. If the
	 *                                  array contains any element not found in {@code expected}. If
	 *                                  {@code name} is blank.
	 */
	ArrayVerifier<A, E> containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain any of {@code expected}
	 */
	ArrayVerifier<A, E> containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the array does not contain any of {@code expected}. If
	 *                                  {@code name} is blank.
	 */
	ArrayVerifier<A, E> containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the array does not contain all of {@code expected}
	 */
	ArrayVerifier<A, E> containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the array does not contain all of {@code expected}. If
	 *                                  {@code name} is blank.
	 */
	ArrayVerifier<A, E> containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the array contains {@code element}
	 */
	ArrayVerifier<A, E> doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the array contains {@code element}. If {@code name} is blank.
	 */
	ArrayVerifier<A, E> doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if the array contains all of the elements in {@code other};
	 *                                  nothing less, nothing more.
	 */
	ArrayVerifier<A, E> doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} or {@code name} are null
	 * @throws IllegalArgumentException if the array contains all of the elements in {@code other};
	 *                                  nothing less, nothing more. If {@code name} is blank.
	 */
	ArrayVerifier<A, E> doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}
	 */
	ArrayVerifier<A, E> doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array contains any of {@code elements}. If {@code name} is blank.
	 */
	ArrayVerifier<A, E> doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}
	 */
	ArrayVerifier<A, E> doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain all of specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the array contains all of {@code elements}. If {@code name} is blank.
	 */
	ArrayVerifier<A, E> doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the array contains any duplicate elements
	 */
	ArrayVerifier<A, E> doesNotContainDuplicates();

	/**
	 * Ensures that the actual value is sorted.
	 *
	 * @param comparator the comparator that defines the order of the elements
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection is not sorted
	 * @see Comparator#naturalOrder()
	 */
	ArrayVerifier<A, E> isSorted(Comparator<E> comparator);

	/**
	 * Returns a verifier over the array's length.
	 *
	 * @return a verifier over the array's length
	 */
	SizeVerifier length();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the array's length
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	ArrayVerifier<A, E> length(Consumer<SizeVerifier> consumer);

	/**
	 * Returns a verifier for the actual value as a collection.
	 *
	 * @return a verifier for the actual value as a collection
	 */
	CollectionVerifier<Collection<E>, E> asCollection();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the actual value as a collection
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	ArrayVerifier<A, E> asCollection(Consumer<CollectionVerifier<Collection<E>, E>> consumer);

	/**
	 * Returns a verifier for the actual value as a list.
	 *
	 * @return a verifier for the actual value as a list
	 */
	ListVerifier<List<E>, E> asList();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the actual value as a list
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	ArrayVerifier<A, E> asList(Consumer<ListVerifier<List<E>, E>> consumer);
}
