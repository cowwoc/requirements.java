/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements10.java.validator.component;

/**
 * Methods that all fixed-precision {@code Number} validators must contain.
 *
 * @param <S> the type of this validator
 */
public interface FixedPrecisionDecimalNumberComponent<S>
{
	/**
	 * Ensures that the value is a well-defined number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is not a number
	 * @see #isNotNumber()
	 * @see Double#isNaN()
	 */
	S isNumber();

	/**
	 * Ensures that the value is the result of a mathematically undefined numerical operation (such as division
	 * by zero) or don't have a representation in real numbers (such as the square-root of -1).
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if value is a well-defined number
	 * @see Double#isNaN()
	 */
	S isNotNumber();

	/**
	 * Ensures that the value is a finite number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>an infinite number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 * @see #isNumber()
	 * @see Double#isInfinite()
	 */
	S isFinite();

	/**
	 * Ensures that the value is an infinite number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>a finite number</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 * @see #isNumber()
	 * @see Double#isInfinite()
	 */
	S isInfinite();
}