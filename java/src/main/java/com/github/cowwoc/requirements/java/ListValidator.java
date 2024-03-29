/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleCollectionValidator;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectValidator;

import java.util.Comparator;
import java.util.List;

/**
 * Validates the requirements of a {@link List}.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectValidator}) imply {@link #isNotNull()}.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public interface ListValidator<L extends List<E>, E>
	extends ExtensibleCollectionValidator<ListValidator<L, E>, L, E>
{
	/**
	 * Ensures that the actual value is sorted.
	 *
	 * @param comparator the comparator that defines the order of the elements
	 * @return the updated validator
	 * @see Comparator#naturalOrder()
	 */
	ListValidator<L, E> isSorted(Comparator<E> comparator);
}