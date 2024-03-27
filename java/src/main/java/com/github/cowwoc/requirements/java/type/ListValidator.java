/*
 * Copyright (c) 2020 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.CollectionPart;
import com.github.cowwoc.requirements.java.type.part.ObjectPart;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Validates the state of a {@link List} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <E> the type of elements in the list
 * @param <T> the type of the list
 */
public interface ListValidator<E, T extends List<E>> extends
	Validator<ListValidator<E, T>>,
	ObjectPart<ListValidator<E, T>, T>,
	CollectionPart<ListValidator<E, T>, E>
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
	ListValidator<E, T> isSorted(Comparator<E> comparator);
}