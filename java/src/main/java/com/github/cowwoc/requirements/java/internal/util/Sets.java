/*
 * Copyright (c) 2016 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Set helper functions.
 */
public final class Sets
{
	/**
	 * Prevent construction.
	 */
	private Sets()
	{
	}

	/**
	 * @param <E>        the type of elements held by the collection
	 * @param collection a collection
	 * @return {@code collection} if it is already a {@code Set}; otherwise, a new {@code Set} containing
	 * {@code collection}'s elements, retaining the iteration order
	 * @throws AssertionError if {@code collection} is null
	 */
	public static <E> Set<E> asSet(Collection<E> collection)
	{
		assert (collection != null) : "collection may not be null";
		if (collection instanceof Set<E> result)
			return result;
		return new LinkedHashSet<>(collection);
	}

	/**
	 * Returns the elements in {@code first} minus the elements in {@code second}, retaining the iteration
	 * order.
	 *
	 * @param <E>    the type of elements in the sets
	 * @param first  the first set
	 * @param second the second set
	 * @return a set containing the elements present in the first set but not in the second set
	 * @throws AssertionError if any of the arguments are null
	 */
	public static <E> Set<E> firstMinusSecond(Set<E> first, Set<E> second)
	{
		assert (first != null) : "first may not be null";
		assert (second != null) : "second may not be null";
		Set<E> result = new LinkedHashSet<>(first);
		result.removeAll(second);
		return result;
	}

	/**
	 * Returns the elements present in both sets, retaining the iteration order of the first set.
	 *
	 * @param <E>    the type of elements in the sets
	 * @param first  the first set
	 * @param second the second set
	 * @return a set containing the elements present in both sets
	 * @throws AssertionError if any of the arguments are null
	 */
	public static <E> Set<E> intersection(Set<E> first, Set<E> second)
	{
		assert (first != null) : "first may not be null";
		assert (second != null) : "second may not be null";
		// Iterate on the smaller of the two sets
		Set<E> small;
		Set<E> big;
		if (first.size() < second.size())
		{
			small = first;
			big = second;
		}
		else
		{
			small = second;
			big = first;
		}
		Set<E> unwanted = new HashSet<>(big);
		unwanted.removeAll(small);

		Set<E> result = new LinkedHashSet<>(big);
		result.removeAll(unwanted);
		return result;
	}
}