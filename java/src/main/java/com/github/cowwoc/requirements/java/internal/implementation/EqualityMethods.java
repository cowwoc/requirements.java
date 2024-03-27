package com.github.cowwoc.requirements.java.internal.implementation;

import com.github.cowwoc.requirements.java.internal.util.Objects;

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
	 * Uses {@link Comparable#compareTo(Object)} to determine whether two objects are equal.
	 */
	public static final BiFunction<Object, Object, Boolean> COMPARABLE = (left, right) ->
		compareTo((Comparable<?>) left, (Comparable<?>) right);

	private EqualityMethods()
	{
	}

	@SuppressWarnings("unchecked")
	private static <C extends Comparable<C>> boolean compareTo(Comparable<?> first, Comparable<?> second)
	{
		C firstComparable = (C) first;
		C secondComparable = (C) second;
		return firstComparable.compareTo(secondComparable) == 0;
	}
}