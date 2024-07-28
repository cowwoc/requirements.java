package com.github.cowwoc.requirements.java.internal.util;

import java.util.Collection;
import java.util.Set;

/**
 * The difference between two collections, irrespective of element ordering.
 *
 * @param <E>          the type of elements in the actual and other collections
 * @param common       elements that were present in both collections
 * @param onlyInActual elements that were only present in the value
 * @param onlyInOther  elements that were only present in the other collection
 */
public record Difference<E>(Set<E> common, Set<E> onlyInActual, Set<E> onlyInOther)
{
	/**
	 * Compares the elements in two collections.
	 *
	 * @param <E>   the type of elements in the collections
	 * @param value the value's elements
	 * @param other the other collection's elements
	 * @return the elements that were common to both collections, or were only present in the value, or were
	 * only present in the other collection
	 */
	public static <E> Difference<E> actualVsOther(Collection<E> value, Collection<E> other)
	{
		Set<E> valueAsSet = Sets.asSet(value);
		Set<E> otherAsSet = Sets.asSet(other);

		Set<E> common = Sets.intersection(valueAsSet, otherAsSet);
		Set<E> onlyInValue = Sets.firstMinusSecond(valueAsSet, otherAsSet);
		Set<E> onlyInOther = Sets.firstMinusSecond(otherAsSet, valueAsSet);
		return new Difference<>(common, onlyInValue, onlyInOther);
	}

	/**
	 * @return {@code true} if both collections contain the same elements, irrespective of ordering
	 */
	public boolean areTheSame()
	{
		return onlyInActual.isEmpty() && onlyInOther.isEmpty();
	}

	/**
	 * @return {@code true} if the collections contain different elements
	 */
	public boolean areDifferent()
	{
		return !onlyInActual.isEmpty() || !onlyInOther.isEmpty();
	}
}