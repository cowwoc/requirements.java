package org.bitbucket.cowwoc.requirements;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Collection helper functions.
 * <p>
 * @author Gili Tzabari
 */
final class Collections
{
	/**
	 * @param <E>        the type of elements held by the collection
	 * @param collection a collection
	 * @return {@code collection} if it is already a {@code Set}; otherwise, a new {@code Set}
	 *         containing {@code collection}'s elements
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
