/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

/**
 * Requirements for a {@link Number}.
 *
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface NumberRequirementsSpi<S extends NumberRequirementsSpi<S, T>, T extends Number & Comparable<? super T>>
	extends ComparableRequirementsSpi<S, T>
{
	/**
	 * Ensures that the parameter is negative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not negative
	 */
	S isNegative();

	/**
	 * Ensures that the parameter is not negative.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is negative
	 */
	S isNotNegative();

	/**
	 * Ensures that the parameter is not positive.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is positive
	 */
	S isNotPositive();

	/**
	 * Ensures that the parameter is not zero.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is zero
	 */
	S isNotZero();

	/**
	 * Ensures that the parameter is positive.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not positive
	 */
	S isPositive();

	/**
	 * Ensures that the parameter is zero.
	 *
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is not zero
	 */
	S isZero();
}
