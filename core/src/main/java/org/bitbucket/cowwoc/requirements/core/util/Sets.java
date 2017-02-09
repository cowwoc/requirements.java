/*
 * Copyright 2016 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Set helper functions.
 *
 * @author Gili Tzabari
 */
public final class Sets
{
	/**
	 * @param <E>        the type of elements held by the collection
	 * @param collection a collection
	 * @return {@code collection} if it is already a {@code Set}; otherwise, a new {@code Set}
	 *         containing {@code collection}'s elements
	 */
	public static <E> Set<E> fromCollection(Collection<E> collection)
	{
		if (collection instanceof Set)
			return (Set<E>) collection;
		return new HashSet<>(collection);
	}

	/**
	 * Returns the difference of {@code first} minus {@code second}, retaining the iteration order.
	 *
	 * @param <E>    the type of elements in the sets
	 * @param first  the first set
	 * @param second the second set
	 * @return a set containing the elements present in the first set but not in the second set
	 */
	public static <E> Set<E> difference(Set<E> first, Set<E> second)
	{
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
	 */
	public static <E> Set<E> intersection(Set<E> first, Set<E> second)
	{
		Set<E> result = new LinkedHashSet<>(first);
		result.removeAll(second);
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private Sets()
	{
	}
}
