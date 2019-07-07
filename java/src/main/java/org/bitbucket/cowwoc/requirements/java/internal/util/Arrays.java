/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.internal.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Array helper functions.
 */
public final class Arrays
{
	/**
	 * @param array an array
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Boolean> asCollection(boolean[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Character> asCollection(char[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Byte> asCollection(byte[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Short> asCollection(short[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Long> asCollection(long[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Integer> asCollection(int[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Float> asCollection(float[] array)
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
	 * @return null if the array is null; otherwise, a collection containing the array elements
	 */
	public static Collection<Double> asCollection(double[] array)
	{
		if (array == null)
			return null;
		List<Double> result = new ArrayList<>(array.length);
		for (double element : array)
			result.add(element);
		return result;
	}

	/**
	 * Prevent construction.
	 */
	private Arrays()
	{
	}
}
