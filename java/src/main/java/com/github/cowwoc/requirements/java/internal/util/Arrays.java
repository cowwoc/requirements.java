/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
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
	 * @return a list containing the array elements
	 */
	public static List<Byte> asList(byte[] array)
	{
		List<Byte> result = new ArrayList<>(array.length);
		for (byte element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Short> asList(short[] array)
	{
		List<Short> result = new ArrayList<>(array.length);
		for (short element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Integer> asList(int[] array)
	{
		List<Integer> result = new ArrayList<>(array.length);
		for (int element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Long> asList(long[] array)
	{
		List<Long> result = new ArrayList<>(array.length);
		for (long element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Boolean> asList(boolean[] array)
	{
		List<Boolean> result = new ArrayList<>(array.length);
		for (boolean element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Character> asList(char[] array)
	{
		List<Character> result = new ArrayList<>(array.length);
		for (char element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Float> asList(float[] array)
	{
		List<Float> result = new ArrayList<>(array.length);
		for (float element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @return a list containing the array elements
	 */
	public static List<Double> asList(double[] array)
	{
		List<Double> result = new ArrayList<>(array.length);
		for (double element : array)
			result.add(element);
		return result;
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code boolean[]} and
	 * {@link java.util.Arrays#equals(boolean[], boolean[])} returns {@code true}
	 */
	public static boolean equals(boolean[] array, Object o)
	{
		return o instanceof boolean[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code char[]} and {@link java.util.Arrays#equals(char[], char[])} returns
	 * {@code true}
	 */
	public static boolean equals(char[] array, Object o)
	{
		return o instanceof char[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code byte[]} and {@link java.util.Arrays#equals(byte[], byte[])} returns
	 * {@code true}
	 */
	public static boolean equals(byte[] array, Object o)
	{
		return o instanceof byte[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code short[]} and {@link java.util.Arrays#equals(short[], short[])}
	 * returns {@code true}
	 */
	public static boolean equals(short[] array, Object o)
	{
		return o instanceof short[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code int[]} and {@link java.util.Arrays#equals(int[], int[])} returns
	 * {@code true}
	 */
	public static boolean equals(int[] array, Object o)
	{
		return o instanceof int[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code long[]} and {@link java.util.Arrays#equals(long[], long[])} returns
	 * {@code true}
	 */
	public static boolean equals(long[] array, Object o)
	{
		return o instanceof long[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code float[]} and {@link java.util.Arrays#equals(float[], float[])}
	 * returns {@code true}
	 */
	public static boolean equals(float[] array, Object o)
	{
		return o instanceof float[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code double[]} and {@link java.util.Arrays#equals(double[], double[])}
	 * returns {@code true}
	 */
	public static boolean equals(double[] array, Object o)
	{
		return o instanceof double[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param <E>   the type of elements in the array
	 * @param array an array
	 * @param o     an object to compare to
	 * @return true if {@code o} is a {@code E[]} and {@link java.util.Arrays#equals(E[], E[])} returns
	 * {@code true}
	 */
	public static <E> boolean equals(E[] array, Object o)
	{
		return o instanceof Object[] other && java.util.Arrays.equals(array, other);
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(byte[] array, byte element)
	{
		for (byte value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(short[] array, short element)
	{
		for (short value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(int[] array, int element)
	{
		for (int value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code o}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(long[] array, long element)
	{
		for (long value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code element}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(boolean[] array, boolean element)
	{
		for (boolean value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code element}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(char[] array, char element)
	{
		for (char value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code element}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(float[] array, float element)
	{
		for (float value : array)
			if (value == element)
				return true;
		return false;
	}

	/**
	 * @param array   an array
	 * @param element the element to search for
	 * @return true if {@code array} contains {@code element}
	 * @throws NullPointerException if {@code array} is null
	 */
	public static boolean contains(double[] array, double element)
	{
		for (double value : array)
			if (value == element)
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

	/**
	 * @param collection a collection
	 * @param <E>        the type of elements in the collection
	 * @param comparator a comparator that indicates the expected order
	 * @return true if the collection is in order
	 */
	public static <E> boolean isSorted(Collection<E> collection, Comparator<E> comparator)
	{
		Iterator<E> i = collection.iterator();
		if (!i.hasNext())
			return true;
		E left = i.next();
		while (i.hasNext())
		{
			E right = i.next();
			if (comparator.compare(left, right) > 0)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array      an array
	 * @param <E>        the type of elements in the array
	 * @param comparator a comparator that indicates the expected order
	 * @return true if the collection is in order
	 */
	public static <E> boolean isSorted(E[] array, Comparator<E> comparator)
	{
		if (array.length == 0)
			return true;
		E left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			E right = array[i];
			if (comparator.compare(left, right) > 0)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(byte[] array)
	{
		if (array.length == 0)
			return true;
		byte left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			byte right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(short[] array)
	{
		if (array.length == 0)
			return true;
		short left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			short right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(int[] array)
	{
		if (array.length == 0)
			return true;
		int left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			int right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(long[] array)
	{
		if (array.length == 0)
			return true;
		long left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			long right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(boolean[] array)
	{
		if (array.length == 0)
			return true;
		boolean left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			boolean right = array[i];
			if (Boolean.compare(left, right) > 0)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(char[] array)
	{
		if (array.length == 0)
			return true;
		char left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			char right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(float[] array)
	{
		if (array.length == 0)
			return true;
		float left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			float right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * @param array an array
	 * @return true if the array is sorted in increasing (natural) order
	 */
	public static boolean isSorted(double[] array)
	{
		if (array.length == 0)
			return true;
		double left = array[0];
		for (int i = 1; i < array.length; ++i)
		{
			double right = array[i];
			if (left > right)
				return false;
			left = right;
		}
		return true;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static byte[] sorted(byte[] array)
	{
		byte[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static short[] sorted(short[] array)
	{
		short[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static int[] sorted(int[] array)
	{
		int[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static long[] sorted(long[] array)
	{
		long[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static boolean[] sorted(boolean[] array)
	{
		// https://cs.stackexchange.com/a/48440/122170
		boolean[] copy = java.util.Arrays.copyOf(array, array.length);
		int trueLength = 0;
		for (boolean value : copy)
			if (value)
				++trueLength;
		int falseLength = copy.length - trueLength;
		for (int i = 0; i < falseLength; ++i)
			copy[i] = false;
		for (int i = 0; i < trueLength; ++i)
			copy[falseLength + i] = true;
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static char[] sorted(char[] array)
	{
		char[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static float[] sorted(float[] array)
	{
		float[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}

	/**
	 * Sorts an array.
	 *
	 * @param array an array
	 * @return a sorted copy of the array
	 */
	public static double[] sorted(double[] array)
	{
		double[] copy = java.util.Arrays.copyOf(array, array.length);
		java.util.Arrays.parallelSort(array);
		return copy;
	}
}