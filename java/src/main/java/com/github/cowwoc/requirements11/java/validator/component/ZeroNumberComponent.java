/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements11.java.validator.component;

/**
 * Methods that validators for numbers that may be zero must contain.
 *
 * @param <S> the type of this validator
 */
public interface ZeroNumberComponent<S>
{
	/**
	 * Ensures that the value is zero. {@code -0.0} is considered to be zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is:
	 *                                  <ul>
	 *                                    <li>not zero</li>
	 *                                    <li>not a number</li>
	 *                                  </ul>
	 */
	S isZero();

	/**
	 * Ensures that the value is not zero. {@code -0.0} is considered to be zero *and* negative.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is {@code null}
	 * @throws IllegalArgumentException if the value is zero
	 */
	S isNotZero();
}