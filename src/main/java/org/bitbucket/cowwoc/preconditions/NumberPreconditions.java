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
	extends CompareToPreconditions<S, T>
{
	/**
	 * Ensures that the parameter is within range.
	 * <p>
	 * @param range the range
	 * @return this
	 * @throws NullPointerException     if {@code range} is null
	 * @throws IllegalArgumentException if {@code parameter} is not in range
	 */
	S isIn(Range<T> range) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is negative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not negative
	 */
	S isNegative() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not negative.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is negative
	 */
	S isNotNegative() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is positive
	 */
	S isNotPositive() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is zero
	 */
	S isNotZero() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is positive.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not positive
	 */
	S isPositive() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is zero.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not zero
	 */
	S isZero() throws IllegalArgumentException;
}
