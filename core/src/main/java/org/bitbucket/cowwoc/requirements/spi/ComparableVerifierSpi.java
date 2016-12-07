/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

/**
 * Verifies a {@link Comparable}.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ComparableVerifierSpi<S extends ComparableVerifierSpi<S, T>, T extends Comparable<? super T>>
	extends ObjectVerifierSpi<S, T>
{
	/**
	 * Ensures that the actual value is greater than a variable.
	 *
	 * @param value the value that the actual value must be greater than
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value};
	 *                                  if {@code name} is empty
	 */
	S isGreaterThan(T value, String name);

	/**
	 * Ensures that the actual value is greater than a constant.
	 *
	 * @param value the value that the actual value must be greater than
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than or equal to {@code value}
	 */
	S isGreaterThan(T value);

	/**
	 * Ensures that the actual value is greater than or bigger than a variable.
	 *
	 * @param value the value that the actual value must be greater than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}; if
	 *                                  {@code name} is empty
	 */
	S isGreaterThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is greater than or bigger than a constant.
	 *
	 * @param value the value that the actual value must be greater than or equal to
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is less than to {@code value}
	 */
	S isGreaterThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is less than the value of a variable.
	 *
	 * @param value the value that the actual value must be less than
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to
	 *                                  {@code value}; if {@code name} is empty
	 */
	S isLessThan(T value, String name);

	/**
	 * Ensures that the actual value is less than a constant.
	 *
	 * @param value the value that the actual value must be less than
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than or equal to {@code value}
	 */
	S isLessThan(T value);

	/**
	 * Ensures that the actual value is less than or equal to a variable.
	 *
	 * @param value the value that the actual value must be less than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code value} or {@code name} are null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}; if
	 *                                  {@code name} is empty
	 */
	S isLessThanOrEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is less than or equal to a constant.
	 *
	 * @param value the value that the actual value must be less than or equal to
	 * @return this
	 * @throws NullPointerException     if {@code value} is null
	 * @throws IllegalArgumentException if the actual value is greater than {@code value}
	 */
	S isLessThanOrEqualTo(T value);

	/**
	 * Ensures that the actual value is within range.
	 *
	 * @param first the first element in the range
	 * @param last  the last element in the range
	 * @return this
	 * @throws NullPointerException     if {@code first} or {@code last} are null
	 * @throws IllegalArgumentException if {@code last} is less than {@code first}; if
	 *                                  the actual value is not in range
	 */
	S isIn(T first, T last);
}
