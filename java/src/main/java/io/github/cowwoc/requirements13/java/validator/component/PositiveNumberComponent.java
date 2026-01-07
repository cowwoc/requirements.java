/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator.component;

/**
 * Methods that validators for numbers that may be positive must contain.
 *
 * @param <S> the type of this validator
 */
public interface PositiveNumberComponent<S>
{
	/**
	 * Ensures that the value is positive.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not positive</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	S isPositive();

	/**
	 * Ensures that the value is not a positive number.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is a positive number
	 */
	S isNotPositive();
}