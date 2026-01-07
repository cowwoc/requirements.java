/*
 * Copyright (c) 2024 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.internal.util;

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
		Set<E> valueAsSet = Collections.asSet(value);
		Set<E> otherAsSet = Collections.asSet(other);

		Set<E> common = Collections.intersection(valueAsSet, otherAsSet);
		Set<E> onlyInValue = Collections.firstMinusSecond(valueAsSet, otherAsSet);
		Set<E> onlyInOther = Collections.firstMinusSecond(otherAsSet, valueAsSet);
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