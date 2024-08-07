package com.github.cowwoc.requirements10.java.internal.util;

/**
 * A collection and the difference between it and the expected value.
 *
 * @param <T>        the type of the collection
 * @param <E>        the type of elements in the collection
 * @param value      a collection
 * @param difference the difference between the actual and expected collections
 */
public record CollectionAndDifference<T, E>(T value, Difference<E> difference)
{
	// DESIGN: T cannot extend Collection<E> because T could be E[] or Collection<E>
}