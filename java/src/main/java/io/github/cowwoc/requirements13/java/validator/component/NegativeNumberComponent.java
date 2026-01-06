/*
 * Copyright (c) 2026 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator.component;

/**
 * Methods that validators for numbers that may be negative must contain.
 *
 * @param <S> the type of this validator
 */
public interface NegativeNumberComponent<S>
{
	/**
	 * Ensures that the value is a negative number. {@code -0.0} is considered to be zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not negative</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	S isNegative();

	/**
	 * Ensures that the value is not a negative number. {@code -0.0} is considered to be zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is a negative number
	 */
	S isNotNegative();
}