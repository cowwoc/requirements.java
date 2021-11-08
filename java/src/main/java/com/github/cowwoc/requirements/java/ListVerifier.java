/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java;

import com.github.cowwoc.requirements.java.extension.ExtensibleCollectionVerifier;
import com.github.cowwoc.requirements.java.extension.ExtensibleObjectVerifier;

import java.util.Comparator;

/**
 * Verifies the requirements of a list.
 * <p>
 * All methods (except those found in {@link ExtensibleObjectVerifier}) imply {@link #isNotNull()}.
 *
 * @param <L> the type of the list
 * @param <E> the type of elements in the list
 */
public interface ListVerifier<L, E> extends ExtensibleCollectionVerifier<ListVerifier<L, E>, L, E>
{
	/**
	 * Ensures that the actual value is sorted.
	 *
	 * @param comparator the comparator that defines the order of the elements
	 * @return the updated verifier
	 * @throws IllegalArgumentException if the collection is not sorted
	 * @see Comparator#naturalOrder()
	 */
	ListVerifier<L, E> isSorted(Comparator<E> comparator);
}