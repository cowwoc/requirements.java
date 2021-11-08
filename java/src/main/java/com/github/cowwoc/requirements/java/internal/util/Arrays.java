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
	 * Prevent construction.
	 */
	private Arrays()
	{
	}
}