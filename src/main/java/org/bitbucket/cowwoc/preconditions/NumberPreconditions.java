/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.preconditions;

import com.google.common.collect.Range;

/**
 * Interface needed for Preconditions.assertThat().
 * <p>
 * @param <S> the type of preconditions that was instantiated
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface NumberPreconditions<S extends NumberPreconditions<S, T>, T extends Number & Comparable<? super T>>
	extends ObjectPreconditions<S, T>
{
	/**
	 * Ensures that the parameter is greater than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to {@code value}
	 */
	S isGreaterThan(T value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than
	 * @return this
	 * @throws IllegalArgumentException if the parameter is less than or equal to {@code value}
	 */
	S isGreaterThan(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than or bigger than a variable.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	S isGreaterThanOrEqualTo(T value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is greater than or bigger than a constant.
	 * <p>
	 * @param value the value the parameter must be greater than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is less than to {@code value}
	 */
	S isGreaterThanOrEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if range is null
	 * @throws IllegalArgumentException if the parameter is not in range
	 */
	S isIn(Range<T> range) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than the value of a variable.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	S isLessThan(T value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than a constant.
	 * <p>
	 * @param value the value the parameter must be less than
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than or equal to
	 *                                  {@code value}
	 */
	S isLessThan(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than or equal to a variable.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @param name  the name of the variable
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	S isLessThanOrEqualTo(T value, String name) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is less than or equal to a constant.
	 * <p>
	 * @param value the value the parameter must be less than or equal to
	 * @return this
	 * @throws IllegalArgumentException if the {@code parameter} is greater than {@code value}
	 */
	S isLessThanOrEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is negative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not negative
	 */
	S isNegative() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not negative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is negative
	 */
	S isNotNegative() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is positive
	 */
	S isNotPositive() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is zero
	 */
	S isNotZero() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not positive
	 */
	S isPositive() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if the parameter is not zero
	 */
	S isZero() throws IllegalArgumentException;
}
