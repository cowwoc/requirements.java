package com.github.cowwoc.requirements11.java.internal.util;

/**
 * Number helper functions.
 */
public final class Numbers
{
	private Numbers()
	{
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(byte number, byte factor)
	{
		return factor != (byte) 0 && (number == (byte) 0 || (number % factor == (byte) 0));
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(short number, short factor)
	{
		return factor != (short) 0 && (number == (short) 0 || (number % factor == (short) 0));
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(int number, int factor)
	{
		return factor != 0 && (number == 0 || (number % factor == 0));
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(long number, long factor)
	{
		return factor != 0L && (number == 0L || (number % factor == 0L));
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(float number, float factor)
	{
		return factor != 0.0f && (number == 0.0f || (number % factor == 0));
	}

	/**
	 * @param number the number being divided
	 * @param factor the number dividing {@code number}
	 * @return true if {@code number} is a multiple of {@code factor}
	 */
	public static boolean isMultipleOf(double number, double factor)
	{
		return factor != 0.0 && (number == 0.0 || (number % factor == 0.0));
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	public static boolean isWholeNumber(float value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1f) == 0f;
	}

	/**
	 * @param value a value
	 * @return true if {@code value} is a whole number
	 */
	public static boolean isWholeNumber(double value)
	{
		// Based on https://stackoverflow.com/a/9909417/14731
		return (value % 1.0) == 0.0;
	}
}