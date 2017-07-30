/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.capabilities;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import org.bitbucket.cowwoc.requirements.core.ObjectVerifier;
import org.bitbucket.cowwoc.requirements.core.Requirements;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * Verifies a value that extends {@link Object} but the implementing verifier is not guaranteed
 * to be an {@link ObjectVerifier}.
 *
 * @param <S> the type of verifier that methods should return
 * @param <T> the type of the value
 * @author Gili Tzabari
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
	 * @param name     the name of the expected value
	 * @param expected the expected value
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is not equal to
	 *                                  the variable
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of
	 * the output format</a>
	 */
	S isEqualTo(String name, Object expected);

	/**
	 * Ensures that the actual value is not equal to a value.
	 *
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the actual value is equal to {@code value}
	 */
	S isNotEqualTo(Object value);

	/**
	 * Ensures that the actual value is not equal to a variable.
	 *
	 * @param name  the name of the variable
	 * @param value the value to compare to
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code name} is empty; if the actual value is equal to the
	 *                                  variable
	 */
	S isNotEqualTo(String name, Object value);

	/**
	 * Ensures that a collection contains the actual value.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain the actual value
	 */
	S isIn(Collection<? super T> collection);

	/**
	 * Ensures that a collection does not contain the actual value.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} contains the actual value
	 */
	S isNotIn(Collection<? super T> collection);

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
	 *         {@link Requirements#assertThat(String, Object) assertThat()} is used when
	 *         assertions are disabled, the verifier does not retain a reference to the actual value)
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
	 *                                if
	 *                                {@link Requirements#assertThat(String, Object) assertThat()}
	 *                                is used when assertions are disabled, the verifier does not need
	 *                                to retain a reference to the actual value)
	 * @see #getActualIfPresent()
	 */
	T getActual();
}
