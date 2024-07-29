package com.github.cowwoc.requirements10.java.internal.util;

import java.util.List;

/**
 * A list and its sorted representation.
 *
 * @param <E>    the type of elements in the list
 * @param value  a list
 * @param sorted the sorted representation of the list
 */
public record ListAndSorted<E>(List<E> value, List<E> sorted)
{
}