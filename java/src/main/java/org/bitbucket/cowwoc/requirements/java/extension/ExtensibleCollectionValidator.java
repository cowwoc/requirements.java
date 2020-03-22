/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.extension;

import org.bitbucket.cowwoc.requirements.java.ArrayValidator;
import org.bitbucket.cowwoc.requirements.java.CollectionValidator;
import org.bitbucket.cowwoc.requirements.java.SizeValidator;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Validates the requirements of a collection of elements but the implementing validator is not guaranteed to
 * be a {@link CollectionValidator}.
 *
 * @param <S> the type of validator returned by the methods
 * @param <C> the type of the collection
 * @param <E> the type of elements in the collection
 */
public interface ExtensibleCollectionValidator<S, C extends Collection<E>, E>
	extends ExtensibleObjectValidator<S, C>
{
	/**
	 * Ensures that the actual value is empty.
	 *
	 * @return the updated validator
	 */
	S isEmpty();

	/**
	 * Ensures that the actual value is not empty.
	 *
	 * @return the updated validator
	 */
	S isNotEmpty();

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param element the element that must exist
	 * @return the updated validator
	 */
	S contains(E element);

	/**
	 * Ensures that the actual value contains an element.
	 *
	 * @param expected the element that must exist
	 * @param name     the name of the expected element
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S contains(E expected, String name);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 * This method is equivalent to an {@link #isEqualTo(Object) equality comparison} that ignores element
	 * ordering.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S containsExactly(Collection<E> expected);

	/**
	 * Ensures that the actual value contains exactly the specified elements; nothing less, nothing more.
	 * This method is equivalent to an {@link #isEqualTo(Object) equality comparison} that ignores element
	 * ordering.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsExactly(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains any of specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S containsAny(Collection<E> expected);

	/**
	 * Ensures that the actual value contains any of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsAny(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S containsAll(Collection<E> expected);

	/**
	 * Ensures that the actual value contains all of the specified elements.
	 *
	 * @param expected the elements that must exist
	 * @param name     the name of the expected elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S containsAll(Collection<E> expected, String name);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @return the updated validator
	 */
	S doesNotContain(E element);

	/**
	 * Ensures that the actual value does not contain an element.
	 *
	 * @param element the element that must not exist
	 * @param name    the name of the element
	 * @return the updated validator
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContain(E element, String name);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S doesNotContainExactly(Collection<E> other);

	/**
	 * Ensures that the actual value does not contain exactly the specified elements; nothing less, nothing more.
	 *
	 * @param other the elements that must not exist
	 * @param name  the name of the collection
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainExactly(Collection<E> other, String name);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S doesNotContainAny(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain any of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainAny(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param expected the elements that must not exist
	 * @return the updated validator
	 * @throws NullPointerException if {@code expected} is null
	 */
	S doesNotContainAll(Collection<E> expected);

	/**
	 * Ensures that the actual value does not contain all of the specified elements.
	 *
	 * @param elements the elements that must not exist
	 * @param name     the name of the elements
	 * @return the updated validator
	 * @throws NullPointerException     if {@code expected} or {@code name} are null
	 * @throws IllegalArgumentException if {@code name} is empty
	 */
	S doesNotContainAll(Collection<E> elements, String name);

	/**
	 * Ensures that the actual value does not contain any duplicate elements.
	 *
	 * @return the updated validator
	 */
	S doesNotContainDuplicates();

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
	 * @return the updated validator
	 * @throws NullPointerException if {@code consumer} is null
	 */
	@SuppressWarnings("LongLine")
	S size(Consumer<SizeValidator> consumer);

	/**
	 * Returns a validator for the actual value as an array.
	 *
	 * @param type the array type
	 * @return a validator for the actual value as an array
	 * @throws NullPointerException if {@code type} is null
	 */
	ArrayValidator<E[], E> asArray(Class<E> type);

	/**
	 * Validates nested requirements. This mechanism can be used to
	 * <a href="https://bitbucket.org/cowwoc/requirements.java/wiki/Features#markdown-header-grouping-nested-requirements">
	 * group related requirements</a>.
	 *
	 * @param type     the array type
	 * @param consumer validates the actual value as an array
	 * @return the updated validator
	 * @throws NullPointerException if {@code type} or {@code consumer} are null
	 */
	@SuppressWarnings("LongLine")
	S asArray(Class<E> type, Consumer<ArrayValidator<E[], E>> consumer);
}
