/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Array helper functions.
 */
public final class Arrays
{
	/**
	 * Prevent construction.
	 */
	private Arrays()
	{
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Boolean> asList(boolean[] array)
	{
		if (array == null)
			return null;
		List<Boolean> result = new ArrayList<>(array.length);
		for (boolean element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Character> asList(char[] array)
	{
		if (array == null)
			return null;
		List<Character> result = new ArrayList<>(array.length);
		for (char element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Byte> asList(byte[] array)
	{
		if (array == null)
			return null;
		List<Byte> result = new ArrayList<>(array.length);
		for (byte element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Short> asList(short[] array)
	{
		if (array == null)
			return null;
		List<Short> result = new ArrayList<>(array.length);
		for (short element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Long> asList(long[] array)
	{
		if (array == null)
			return null;
		List<Long> result = new ArrayList<>(array.length);
		for (long element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Integer> asList(int[] array)
	{
		if (array == null)
			return null;
		List<Integer> result = new ArrayList<>(array.length);
		for (int element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Float> asList(float[] array)
	{
		if (array == null)
			return null;
		List<Float> result = new ArrayList<>(array.length);
		for (float element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static List<Double> asList(double[] array)
	{
		if (array == null)
			return null;
		List<Double> result = new ArrayList<>(array.length);
		for (double element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @return null if the array is null; otherwise, a list containing the array elements
	 */
	public static <E> List<E> asList(E[] array)
	{
		if (array == null)
			return null;
		return java.util.Arrays.asList(array);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code boolean[]} and
	 * {@link java.util.Arrays#equals(boolean[], boolean[])} returns {@code true}
	 */
	public static boolean equals(boolean[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof boolean[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code char[]} and
	 * {@link java.util.Arrays#equals(char[], char[])} returns {@code true}
	 */
	public static boolean equals(char[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof char[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code byte[]} and
	 * {@link java.util.Arrays#equals(byte[], byte[])} returns {@code true}
	 */
	public static boolean equals(byte[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof byte[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code short[]} and
	 * {@link java.util.Arrays#equals(short[], short[])} returns {@code true}
	 */
	public static boolean equals(short[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof short[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code long[]} and
	 * {@link java.util.Arrays#equals(long[], long[])} returns {@code true}
	 */
	public static boolean equals(long[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof long[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code int[]} and
	 * {@link java.util.Arrays#equals(int[], int[])} returns {@code true}
	 */
	public static boolean equals(int[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof int[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code float[]} and
	 * {@link java.util.Arrays#equals(float[], float[])} returns {@code true}
	 */
	public static boolean equals(float[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof float[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code double[]} and
	 * {@link java.util.Arrays#equals(double[], double[])} returns {@code true}
	 */
	public static boolean equals(double[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof double[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code E[]} and
	 * {@link java.util.Arrays#equals(E[], E[])} returns {@code true}
	 */
	public static <E> boolean equals(E[] array, Object o)
	{
		if (array == o)
			return true;
		if (array == null || o == null)
			return false;
		if (!(o instanceof Object[] other))
			return false;
		return java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(boolean[] array, Object o)
	{
		if (!(o instanceof Boolean other))
			return false;
		for (boolean value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(char[] array, Object o)
	{
		if (!(o instanceof Character other))
			return false;
		for (char value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(byte[] array, Object o)
	{
		if (!(o instanceof Byte other))
			return false;
		for (byte value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(short[] array, Object o)
	{
		if (!(o instanceof Short other))
			return false;
		for (short value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(long[] array, Object o)
	{
		if (!(o instanceof Long other))
			return false;
		for (long value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(int[] array, Object o)
	{
		if (!(o instanceof Integer other))
			return false;
		for (int value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(float[] array, Object o)
	{
		if (!(o instanceof Float other))
			return false;
		for (float value : array)
			if (value == other)
				return true;
		return false;
	}

	/**
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(double[] array, Object o)
	{
		if (!(o instanceof Double other))
			return false;
		for (double value : array)
			if (Double.compare(value, other) == 0)
				return true;
		return false;
	}

	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @param o     the object to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static <E> boolean contains(E[] array, Object o)
	{
		for (E value : array)
			if (Objects.equals(value, o))
				return true;
		return false;
	}
}