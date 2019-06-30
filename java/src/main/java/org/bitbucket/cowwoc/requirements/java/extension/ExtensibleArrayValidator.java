/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Validates the requirements of an array of elements but the implementing validator is not guaranteed to be a
 * {@link ArrayValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <E> the Object representation of the array elements
 * @param <A> the type of the array
 */
public interface ExtensibleArrayValidator<S, E, A> extends ExtensibleObjectValidator<S, A>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 */
	S isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @return this
	 */
	S contains(E expected);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element
	 * @param name     the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains the specified elements; nothing less, nothing more.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 */
	S doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return this
	 */
	S doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 */
	S doesNotContainAny(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @return this
	 */
	S doesNotContainAll(Collection<E> elements);

	/**
	 * Ensures that the actual value does not contain all of specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 */
	S doesNotContainDuplicates();

	/**
	 * Returns a validator over the array's length.
	 *
	 * @return a validator over the array's length
	 */
	SizeValidator length();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the array's length
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S length(Consumer<SizeValidator> consumer);

	/**
	 * Returns a validator for the actual value as a collection.
	 *
	 * @return a validator for the actual value as a collection
	 */
	CollectionValidator<Collection<E>, E> asCollection();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the actual value as a collection
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S asCollection(Consumer<CollectionValidator<Collection<E>, E>> consumer);
}
