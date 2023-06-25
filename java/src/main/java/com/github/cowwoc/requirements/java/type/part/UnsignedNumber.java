/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type.part;

import com.github.cowwoc.requirements.java.ConfigurationUpdater;

import java.util.function.Function;

/**
 * Methods that all unsigned {@code Number} validators must contain.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 *
 * @param <S> the type of this validator
 */
public interface UnsignedNumber<S>
{
	/**
	 * Ensures that the value is zero. {@code -0.0} is considered to be equal to zero.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not zero
	 */
	S isZero();

	/**
	 * Ensures that the value is not zero. {@code -0.0} is considered to be equal to zero.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is zero
	 */
	S isNotZero();

	/**
	 * Ensures that the value is positive.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is not positive
	 */
	S isPositive();

	/**
	 * Ensures that the value is not positive.
	 *
	 * @return this
	 * @throws NullPointerException     if the value is null
	 * @throws IllegalArgumentException if the value is positive
	 */
	S isNotPositive();
}