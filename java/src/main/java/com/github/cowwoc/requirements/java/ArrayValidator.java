/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Validates the requirements of an array of elements.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <A> the type of the array
 * @param <E> the type of elements in the array
 */
public interface ArrayValidator<A, E> extends ExtensibleObjectValidator<ArrayValidator<A, E>, A>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 */
	ArrayValidator<A, E> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated validator
	 */
	ArrayValidator<A, E> isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @return the updated validator
	 */
	ArrayValidator<A, E> contains(E expected);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> contains(E expected, String name);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return the updated validator
	 */
	ArrayValidator<A, E> doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	ArrayValidator<A, E> doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain all of specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	ArrayValidator<A, E> doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return the updated validator
	 */
	ArrayValidator<A, E> doesNotContainDuplicates();

	/**
	 * Returns a validator over the array's length.
	 *
	 * @return a validator over the array's length
	 */
	SizeValidator length();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the array's length
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	ArrayValidator<A, E> length(Consumer<SizeValidator> consumer);

	/**
	 * Returns a validator for the actual value as a collection.
	 *
	 * @return a validator for the actual value as a collection
	 */
	CollectionValidator<Collection<E>, E> asCollection();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://github.com/cowwoc/requirements.java/wiki/Features.md#grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the actual value as a collection
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	ArrayValidator<A, E> asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer);
}
