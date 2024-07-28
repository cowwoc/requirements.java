/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.validator;

import com.github.cowwoc.requirements.java.validator.component.CollectionComponent;
import com.github.cowwoc.requirements.java.validator.component.ValidatorComponent;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.validator.component.ObjectComponent;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Validates the state of a {@link List}.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
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