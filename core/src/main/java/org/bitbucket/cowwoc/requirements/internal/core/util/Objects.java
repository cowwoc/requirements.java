/*
 * Copyright 2017 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.internal.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Object helper functions.
 *
 * @author Gili Tzabari
 */
public final class Objects
{
	/**
	 * Compares two objects, wrapping arrays to implement equality.
	 *
	 * @param first  an object
	 * @param second an object
	 * @return true if the first object is equal to the second
	 */
	public static boolean equals(Object first, Object second)
	{
		return java.util.Objects.equals(normalize(first), normalize(second));
	}

	/**
	 * If the input is an array, wraps it into an object that implements {@code equals()},
	 * {@code hashCode()} and {@code toString()}.
	 *
	 * @param o an object
	 * @return an object that implements {@code equals()}, {@code hashCode()} and {@code toString()}
	 */
	private static Object normalize(Object o)
	{
		if (o == null)
			return null;
		Class<?> type = o.getClass();
		if (type.isArray())
			return asList(o, type);
		return o;
	}

	/**
	 * @param array an array
	 * @param type  the type of the array
	 * @return a list containing the elements of the array
	 * @throws AssertionError if any of the arguments are null
	 */
	private static List<?> asList(Object array, Class<?> type)
	{
		assert (array != null);
		assert (type != null);
		if (!type.getComponentType().isPrimitive())
			return Arrays.asList((Object[]) array);
		if (type == boolean[].class)
		{
			boolean[] valueAsArray = (boolean[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == char[].class)
		{
			char[] valueAsArray = (char[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == short[].class)
		{
			short[] valueAsArray = (short[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == int[].class)
		{
			int[] valueAsArray = (int[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == long[].class)
		{
			long[] valueAsArray = (long[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == float[].class)
		{
			float[] valueAsArray = (float[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		if (type == double[].class)
		{
			double[] valueAsArray = (double[]) array;
			return IntStream.range(0, valueAsArray.length).mapToObj(i -> valueAsArray[i]).
				collect(Collectors.toList());
		}
		throw new AssertionError("Unexpected array type: " + type);
	}

	/**
	 * Prevent construction.
	 */
	private Objects()
	{
	}
}
