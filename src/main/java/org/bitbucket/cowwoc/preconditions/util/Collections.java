package org.bitbucket.cowwoc.preconditions.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ImmutableCollection helper functions.
 * <p>
 * @author Gili Tzabari
 */
public final class Collections
{
	/**
	 * @param <E>        the type of elements held by the collection
	 * @param collection a collection
	 * @return if {@code collection} is already a {@code List} it is returned; otherwise, a new
	 *         {@code List} containing {@code collection}'s elements
	 */
	public static <E> List<E> asList(Collection<E> collection)
	{
		if (collection instanceof List)
			return (List<E>) collection;
		return new ArrayList<>(collection);
	}

	/**
	 * @param <E>        the type of elements held by the collection
	 * @param collection a collection
	 * @return if {@code collection} is already an {@code HashSet} it is returned; otherwise, a new
	 *         {@code HashSet} containing {@code collection}'s elements
	 */
	public static <E> Set<E> asSet(Collection<E> collection)
	{
		if (collection instanceof Set)
			return (Set<E>) collection;
		return new HashSet<>(collection);
	}

	/**
	 * Prevent construction.
	 */
	private Collections()
	{
	}
}
