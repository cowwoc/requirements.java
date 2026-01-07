/*
 * Copyright (c) 2023 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package io.github.cowwoc.requirements13.java.validator.component;

import io.github.cowwoc.requirements13.java.GenericType;
import io.github.cowwoc.requirements13.java.validator.ObjectValidator;
import io.github.cowwoc.requirements13.annotation.CheckReturnValue;

/**
 * Methods that all object validators must contain.
 *
 * @param <S> the type of this validator
 * @param <T> the type of the value that is being validated
 */
public interface ObjectComponent<S, T>
{
	/**
	 * Returns the value that is being validated.
	 *
	 * @return the value
	 * @throws IllegalStateException if a previous validation failed
	 */
	@CheckReturnValue
	T getValue();

	/**
	 * Ensures that the value is {@code null}.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the value is not null
	 */
	S isNull();

	/**
	 * Ensures that the value is not null.
	 * <p>
	 * This method should be used to validate method arguments that are assigned to class fields but not
	 * accessed right away (such as constructor and setter arguments). It should also be used to validate any
	 * method arguments when the validator contains
	 * {@link ValidatorComponent#getContext() additional contextual information}. Otherwise, the default Java
	 * handler is preferable because it throws {@code NullPointerException} with a helpful message.
	 *
	 * @return this
	 * @throws NullPointerException if the value is {@code null}
	 */
	S isNotNull();

	/**
	 * Ensures that the value is the same reference {@code expected}.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return this
	 * @throws NullPointerException     if {@code name} is {@code null}
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value does not reference the same value as
	 *                                    {@code expected}</li>
	 *                                  </ul>
	 */
	S isReferenceEqualTo(Object expected, String name);

	/**
	 * Ensures that the value is not the same reference as {@code unwanted}.
	 *
	 * @param unwanted the unwanted object
	 * @param name     the name of the unwanted object
	 * @return this
	 * @throws NullPointerException     if {@code name} is {@code null}
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value references the same value as {@code unwanted}</li>
	 *                                  </ul>
	 */
	S isReferenceNotEqualTo(Object unwanted, String name);

	/**
	 * Ensures that the object is an instance of a class.
	 *
	 * @param <U>      the desired class
	 * @param expected the desired class. For types that contain type-parameters, use the
	 *                 {@link #isInstanceOf(GenericType) TypeToken} overload.
	 * @return a validator for an object of the desired class
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the value is not an instance of the desired class
	 */
	<U> ObjectValidator<U> isInstanceOf(Class<U> expected);

	/**
	 * Ensures that the object is an instance of a class.
	 *
	 * @param <U>      the desired class
	 * @param expected the desired class. For types without type-parameters, prefer the
	 *                 {@link #isInstanceOf(Class) Class} overload.
	 * @return a validator for an object of the desired class
	 * @throws NullPointerException     if the value or {@code expected} are null
	 * @throws IllegalArgumentException if the value is not an instance of the desired class
	 */
	<U> ObjectValidator<U> isInstanceOf(GenericType<U> expected);

	/**
	 * Ensures that the object is not an instance of a class.
	 *
	 * @param unwanted the unwanted class. For types that contain type-parameters, use the
	 *                 {@link #isInstanceOf(GenericType) TypeToken} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the value is an instance of the unwanted class
	 */
	S isNotInstanceOf(Class<?> unwanted);

	/**
	 * Ensures that the object is not an instance of a class.
	 *
	 * @param unwanted the unwanted class. For types without type-parameters, prefer the
	 *                 {@link #isInstanceOf(Class) Class} overload.
	 * @return this
	 * @throws NullPointerException     if the value or {@code unwanted} are null
	 * @throws IllegalArgumentException if the value is an instance of the unwanted class
	 */
	S isNotInstanceOf(GenericType<?> unwanted);

	/**
	 * Ensures that the object is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the value is not equal to {@code expected}
	 * @see <a href="https://github.com/cowwoc/requirements.java/blob/main/docs/textual_diff.md">An
	 * explanation of the output format</a>
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the object is equal to {@code expected}.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is {@code null}
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is not equal to {@code expected}</li>
	 *                                  </ul>
	 * @see <a href="https://github.com/cowwoc/requirements.java/blob/main/docs/textual_diff.md">An
	 * explanation of the output format</a>
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the object is not equal to {@code unwanted}.
	 *
	 * @param unwanted the unwanted value
	 * @return this
	 * @throws IllegalArgumentException if the value is equal to {@code expected}
	 * @see <a href="https://github.com/cowwoc/requirements.java/blob/main/docs/textual_diff.md">An
	 * explanation of the output format</a>
	 */
	S isNotEqualTo(Object unwanted);

	/**
	 * Ensures that the object is not equal to {@code unwanted}.
	 *
	 * @param unwanted the unwanted value
	 * @param name     the name of the other value
	 * @return this
	 * @throws NullPointerException     if {@code name} is {@code null}
	 * @throws IllegalArgumentException if:
	 *                                  <ul>
	 *                                    <li>{@code name} is empty</li>
	 *                                    <li>{@code name} contains whitespace</li>
	 *                                    <li>{@code name} is already in use by the value being validated or
	 *                                    the validator context</li>
	 *                                    <li>the value is equal to the {@code unwanted} value</li>
	 *                                  </ul>
	 * @see <a href="https://github.com/cowwoc/requirements.java/blob/main/docs/textual_diff.md">An
	 * explanation of the output format</a>
	 */
	S isNotEqualTo(Object unwanted, String name);
}