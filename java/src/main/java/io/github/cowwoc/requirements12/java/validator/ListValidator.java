/*
 * Copyright (c) 2025 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements12.java.validator;

import io.github.cowwoc.requirements12.java.validator.component.CollectionComponent;
import io.github.cowwoc.requirements12.java.validator.component.ObjectComponent;
import io.github.cowwoc.requirements12.java.validator.component.ValidatorComponent;

import java.util.Comparator;
import java.util.List;

/**
 * Validates the state of a {@link List}.
 *
 * @param <T> the type of the list
 * @param <E> the type of elements in the list
 */
public interface ListValidator<T extends List<E>, E> extends
	ValidatorComponent<ListValidator<T, E>, T>,
	ObjectComponent<ListValidator<T, E>, T>,
	CollectionComponent<ListValidator<T, E>, E>
{
	/**
	 * Ensures that the list is sorted.
	 *
	 * @param comparator the comparator that defines the order of the elements
	 * @return this
	 * @throws NullPointerException     if the value or {@code comparator} are null
	 * @throws IllegalArgumentException if the list is not sorted
	 * @see Comparator#naturalOrder()
	 */
	ListValidator<T, E> isSorted(Comparator<E> comparator);
}