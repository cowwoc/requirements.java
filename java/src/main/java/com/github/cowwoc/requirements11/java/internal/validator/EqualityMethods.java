package com.github.cowwoc.requirements11.java.internal.validator;

import com.github.cowwoc.requirements11.java.internal.util.Objects;

import java.util.function.BiFunction;

/**
 * The built-in equality methods.
 */
public final class EqualityMethods
{
	/**
	 * Uses {@link Object#equals(Object)} to determine whether two objects are equal.
	 */
	public static final BiFunction<Object, Object, Boolean> OBJECT = Objects::equals;
	/**
	 * Uses {@link Comparable#compareTo(Object)} to determine the equality of non-null objects. If either
	 * argument is null, the equality operator {@code ==} is used instead.
	 */
	public static final BiFunction<Object, Object, Boolean> COMPARABLE = (left, right) ->
		compareTo((Comparable<?>) left, (Comparable<?>) right);

	private EqualityMethods()
	{
	}

	@SuppressWarnings("unchecked")
	private static <C extends Comparable<C>> boolean compareTo(Comparable<?> first, Comparable<?> second)
	{
		if (first == null || second == null)
			return first == second;
		C firstComparable = (C) first;
		C secondComparable = (C) second;
		return firstComparable.compareTo(secondComparable) == 0;
	}
}