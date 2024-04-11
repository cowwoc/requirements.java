package com.github.cowwoc.requirements.java.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Collection helper functions.
 */
public final class Collections
{
	private Collections()
	{
	}

	/**
	 * Returns the elements in {@code first} minus the elements in {@code second}, retaining the iteration
	 * order.
	 *
	 * @param <E>        the type of elements in the collections
	 * @param first      a collection of elements
	 * @param second     another collection of elements
	 * @param firstLeft  the elements present in the first collection but not in the second collection
	 * @param secondLeft the elements present in the second collection but not in the first collection
	 * @throws NullPointerException if any of the arguments are null
	 */
	public static <E> void difference(Collection<E> first, Collection<E> second, List<E> firstLeft,
		List<E> secondLeft)
	{
		Map<E, Integer> valueElementToCount = getElementToCount(first);
		Map<E, Integer> expectedElementToCount = getElementToCount(second);

		for (Entry<E, Integer> entry : expectedElementToCount.entrySet())
		{
			valueElementToCount.merge(entry.getKey(), entry.getValue(),
				(firstCount, secondCount) ->
				{
					int count = firstCount - secondCount;
					if (count == 0)
						return null;
					return count;
				});
		}

		for (Entry<E, Integer> entry : valueElementToCount.entrySet())
		{
			int count = entry.getValue();
			while (count < 0)
			{
				secondLeft.add(entry.getKey());
				++count;
			}
			while (count > 0)
			{
				firstLeft.add(entry.getKey());
				--count;
			}
		}
	}

	/**
	 * @param <E>        the type of elements in the collection
	 * @param collection a collection
	 * @return a mapping between each element and the number of times it shows up in the value
	 */
	private static <E> Map<E, Integer> getElementToCount(Collection<E> collection)
	{
		Map<E, Integer> elementToCount = HashMap.newHashMap(collection.size());
		for (E element : collection)
			elementToCount.merge(element, 1, Integer::sum);
		return elementToCount;
	}

	/**
	 * @param first  a collection
	 * @param second another collection
	 * @return true if {@code first} contains any elements in {@code second}
	 */
	public static boolean containsAny(Collection<?> first, Collection<?> second)
	{
		return java.util.Collections.disjoint(first, second);
	}
}