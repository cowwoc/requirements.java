package com.github.cowwoc.requirements10.java.internal.util;

import java.util.Set;

/**
 * A collection and the set of its duplicate elements.
 *
 * @param <T>        the type of the collection
 * @param <E>        the type of the elements in the collection
 * @param value      the collection
 * @param duplicates the duplicate elements in the collection
 */
public record CollectionAndDuplicates<T, E>(T value, Set<E> duplicates)
{
}