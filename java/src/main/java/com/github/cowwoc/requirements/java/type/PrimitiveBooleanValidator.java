/*
 * Copyright (c) 2019 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.cowwoc.requirements.java.type;

import com.github.cowwoc.requirements.annotation.CheckReturnValue;
import com.github.cowwoc.requirements.java.ConfigurationUpdater;
import com.github.cowwoc.requirements.java.type.part.Validator;

import java.util.function.Function;

/**
 * Validates the state of a {@code boolean} value.
 * <p>
 * <b>NOTE</b>: Methods in this class throw or record exceptions under the conditions specified in their
 * Javadoc. However, the actual exception type that is thrown or recorded may be different from what the
 * Javadoc indicates, depending on the value of the
 * {@link ConfigurationUpdater#exceptionTransformer(Function)} setting. This allows users to customize the
 * exception handling behavior of the class.
 */
public interface PrimitiveBooleanValidator extends Validator<PrimitiveBooleanValidator>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the validated value, or {@code false} if a validation failed
	 */
	@CheckReturnValue
	boolean getValue();

	/**
	 * Returns the value that is being validated.
	 *
	 * @param defaultValue the fallback value in case of a validation failure
	 * @return the validated value, or {@code defaultValue} if a validation failed
	 */
	@CheckReturnValue
	boolean getValueOrDefault(boolean defaultValue);

	/**
	 * Ensures that the value is {@code true}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is {@code false}
	 */
	PrimitiveBooleanValidator isTrue();

	/**
	 * Ensures that the value is {@code false}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is {@code true}
	 */
	PrimitiveBooleanValidator isFalse();

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	PrimitiveBooleanValidator isEqualTo(boolean expected);

	/**
	 * Ensures that the value is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is not equal to {@code expected}.
	 * @see ConfigurationUpdater#equalityMethod() The method used to compare objects
	 */
	PrimitiveBooleanValidator isEqualTo(boolean expected, String name);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code unwanted}
	 */
	PrimitiveBooleanValidator isNotEqualTo(boolean unwanted);

	/**
	 * Ensures that the value is not equal to {@code unwanted}.
	 *
	 * @param unwanted the value to compare to
	 * @param name     the name of the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} contains leading or trailing whitespace, or is empty. If
	 *                                  the value is equal to {@code unwanted}.
	 */
	PrimitiveBooleanValidator isNotEqualTo(boolean unwanted, String name);

	/**
	 * Returns a validator for the boxed representation of the value.
	 *
	 * @return a validator for the boxed representation of the value
	 */
	BooleanValidator asBoxed();
}