/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.extension;

import com.github.cowwoc.requirements.java.ArrayVerifier;
import com.github.cowwoc.requirements.java.CollectionVerifier;
import com.github.cowwoc.requirements.java.SizeVerifier;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a collection of elements but the implementing verifier is not guaranteed to
 * be a {@link CollectionVerifier}.
 *
 * @param <S> the type of verifier returned by the methods
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface ExtensibleCollectionVerifier<S, C extends Collection<E>, E>
	extends ExtensibleObjectVerifier<S, C>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection is not empty
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection is empty
	 */
	S isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection does not contain {@code expected}
	 */
	S contains(E expected);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection does not contain {@code expected}. If {@code name} is
	 *                                  empty.
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection is missing any element found in {@code expected};
	 *                                  if the collection contains any element not found in {@code expected}
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection is missing any element found in {@code expected}. If
	 *                                  the collection contains any element not found in {@code expected}. If
	 *                                  {@code name} is empty.
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code expected}
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain any of {@code expected}. If
	 *                                  {@code name} is empty.
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} is null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code expected}
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if the collection does not contain all of {@code expected}. If
	 *                                  {@code name} is empty.
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection contains {@code element}
	 */
	S doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the collection contains {@code element}. If {@code name} is empty.
	 */
	S doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} is null
	 * @throws IllegalArgumentException if the collection contains all of the elements in {@code other};
	 *                                  nothing less, nothing more.
	 */
	S doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code other} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains all of the elements in {@code other};
	 *                                  nothing less, nothing more. If {@code name} is empty.
	 */
	S doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}
	 */
	S doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains any of {@code elements}. If {@code name} is
	 *                                  empty.
	 */
	S doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} is null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}
	 */
	S doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain all of specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated verifier
	 * @throws NullPointerException     if {@code elements} or {@code name} are null
	 * @throws IllegalArgumentException if the collection contains all of {@code elements}. If {@code name} is
	 *                                  empty.
	 */
	S doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection contains any duplicate elements
	 */
	S doesNotContainDuplicates();

	/**
	 * Returns a verifier over the collection's size.
	 *
	 * @return a verifier over the collection's size
	 */
	SizeVerifier size();

	/**
	 * Verifies nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer verifies the collection's size
	 * @return the updated verifier
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S size(Consumer<SizeVerifier> consumer);

	/**
	 * Returns a verifier for the actual value as an array.
	 *
	 * @param type the array type
	 * @return a verifier for the actual value as an array
	 * @throws NullPointerException if {@code type} is null
	 */
	ArrayVerifier<E[], E> asArray(Class<E> type);

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param type     the array type
	 * @param consumer validates the actual value as an array
	 * @return the updated verifier
	 * @throws NullPointerException if {@code type} or {@code consumer} are null
	 */
	@SuppressWarnings("LongLine")
	S asArray(Class<E> type, Consumer<ArrayVerifier<E[], E>> consumer);
}
