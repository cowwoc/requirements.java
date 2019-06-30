/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java;

import org.bitbucket.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Validates the requirements of a {@link Collection}.
 *
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface CollectionValidator<C extends Collection<E>, E>
	extends ExtensibleObjectValidator<CollectionValidator<C, E>, C>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return this
	 */
	CollectionValidator<C, E> isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return this
	 */
	CollectionValidator<C, E> isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return this
	 */
	CollectionValidator<C, E> contains(E element);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element that must exist
	 * @param name     the name of the expected element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> contains(E expected, String name);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 * This method is equivalent to an {@link #isEqualTo(Object) equality comparison} that ignores element
	 * ordering.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	CollectionValidator<C, E> containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 * This method is equivalent to an {@link #isEqualTo(Object) equality comparison} that ignores element
	 * ordering.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	CollectionValidator<C, E> containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return this
	 */
	CollectionValidator<C, E> containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return this
	 */
	CollectionValidator<C, E> doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return this
	 */
	CollectionValidator<C, E> doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return this
	 */
	CollectionValidator<C, E> doesNotContainAny(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return this
	 */
	CollectionValidator<C, E> doesNotContainAll(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	CollectionValidator<C, E> doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return this
	 */
	CollectionValidator<C, E> doesNotContainDuplicates();

	/**
	 * Returns a validator for the collection's size.
	 *
	 * @return a validator for the collection's size
	 */
	SizeValidator size();

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param consumer validates the collection's size
	 * @return this
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	CollectionValidator<C, E> size(Consumer<SizeValidator> consumer);

	/**
	 * Returns a validator for the actual value as an array.
	 *
	 * @param type the array type
	 * @return a validator for the actual value as an array
	 * @throws NullPointerException if {@code type} is null
	 */
	ArrayValidator<E> asArray(Class<E> type);

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param type     the array type
	 * @param consumer validates the actual value as an array
	 * @return this
	 * @throws NullPointerException if {@code type} or {@code consumer} are null
	 */
	@SuppressWarnings("LongLine")
	CollectionValidator<C, E> asArray(Class<E> type, Consumer<ArrayValidator<E>> consumer);
}