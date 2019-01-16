/*
 * Copyright (c) 2015 Gili Tzabari
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.java.capabilities;

import org.bitbucket.cowwoc.requirements.java.JavaVerifier;
import org.bitbucket.cowwoc.requirements.java.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.java.StringVerifier;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Verifies the requirements of a value that extends {@link Object} but the implementing verifier is not guaranteed
 * to be an {@link ObjectVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 */
public interface ObjectCapabilities<S, T>
{
	/**
	 * Ensures that the actual value is equal to an expected value.
	 *
	 * @param expected the expected value
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not equal to value
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of
	 * the output format</a>
	 */
	S isEqualTo(Object expected);

	/**
	 * Ensures that the actual value is equal to the expected value.
	 *
	 * @param expected the expected value
	 * @param name     the name of the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is not equal to {@code expected}. If {@code name} is empty.
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(Object expected, String name);

	/**
	 * Ensures that the actual value is not equal to another value.
	 *
	 * @param other the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the actual value is equal to {@code value}
	 */
	S isNotEqualTo(Object other);

	/**
	 * Ensures that the actual value is not equal to another variable.
	 *
	 * @param other the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is equal to {@code other}. If {@code name} is empty.
	 */
	S isNotEqualTo(Object other, String name);

	/**
	 * Ensures that the actual and expected objects are one and the same object.
	 *
	 * @param expected the expected object
	 * @param name     the name of the expected object
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code actual != expected}. If {@code name} is empty.
	 */
	S isSameObjectAs(Object expected, String name);

	/**
	 * Ensures that the actual value does not reference a specific object.
	 *
	 * @param other the object to compare to
	 * @param name  the name of the other object
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code actual == other}. If {@code name} is empty.
	 */
	S isNotSameObjectAs(Object other, String name);

	/**
	 * Ensures that the actual value is one of the elements in a collection. This is typically used to ensure that the actual value is
	 * valid.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain the actual value
	 */
	S isOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is not one of the elements in a collection. This is typically used to ensure that the actual value is
	 * not equal to one or more reserved values.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} contains the actual value
	 */
	S isNotOneOf(Collection<? super T> collection);

	/**
	 * Ensures that the actual value is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if the actual value is not an instance of {@code type}
	 */
	S isInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is not an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} is null
	 * @throws IllegalArgumentException if the actual value is an instance of {@code type}
	 */
	S isNotInstanceOf(Class<?> type);

	/**
	 * Ensures that the actual value is null.
	 *
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not null
	 */
	S isNull();

	/**
	 * Ensures that the actual value is not null.
	 *
	 * @return this
	 * @throws NullPointerException if the actual value is null
	 */
	S isNotNull();

	/**
	 * @return a verifier for the String representation of the actual value
	 */
	StringVerifier asString();

	/**
	 * @param consumer verifies the String representation of the actual value
	 * @return this
	 */
	S asString(Consumer<StringVerifier> consumer);

	/**
	 * Returns the actual value.
	 * <p>
	 * NOTE: The behavior of verifiers whose value is modified is undefined. Special care should be
	 * taken to avoid modifying the returned value, or avoid the use of verifiers after their value is
	 * modified.
	 *
	 * @return {@code Optional.empty()} if the actual value is not available (e.g. if
	 * {@link JavaVerifier#assertThat(Object, String) assertThat()} is used when
	 * assertions are disabled, the verifier does not retain a reference to the actual value)
	 */
	Optional<T> getActualIfPresent();

	/**
	 * Returns the actual value.
	 * <p>
	 * NOTE: The behavior of verifiers whose value is modified is undefined. Special care should be
	 * taken to avoid modifying the returned value, or avoid the use of verifiers after their value is
	 * modified.
	 *
	 * @return the actual value
	 * @throws NoSuchElementException if the verifier does not have access to the actual value (e.g.
	 *                                if {@link JavaVerifier#assertThat(Object, String) assertThat()}
	 *                                is used when assertions are disabled, the verifier does not need
	 *                                to retain a reference to the actual value)
	 * @see #getActualIfPresent()
	 */
	T getActual();
}
