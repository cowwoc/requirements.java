/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.ext;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.bitbucket.cowwoc.requirements.core.Requirements;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * Verifies an {@link Object} parameter.
 * <p>
 * @param <S> the type of the non-extension interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectVerifierExtension<S extends ObjectVerifierExtension<S, T>, T>
{
	/**
	 * Ensures that the actual value is equal to a value.
	 *
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the actual value is not equal to value
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value);

	/**
	 * Ensures that the actual value is equal to a variable.
	 *
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is not equal to the variable; if
	 *                                  {@code name} is empty
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value, String name);

	/**
	 * Ensures that the actual value is not equal to a value.
	 *
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if the actual value is equal to value
	 */
	S isNotEqualTo(T value);

	/**
	 * Ensures that the actual value is not equal to a variable.
	 *
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if the actual value is equal to the variable; if {@code name}
	 *                                  is empty
	 */
	S isNotEqualTo(T value, String name);

	/**
	 * Ensures that a collection contains the actual value.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain the actual value
	 */
	S isIn(Collection<T> collection);

	/**
	 * Ensures that the actual value is an instance of a class.
	 *
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} are null
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
	 * @return a verifier for the String representation of the value
	 */
	StringVerifier asString();

	/**
	 * Returns the actual value.
	 * <p>
	 * Beware: the return value may be wrapped to prevent modification.
	 *
	 * @return {@code Optional.empty()} if the verifier does not have access to the actual value (e.g.
	 *         if {@link Requirements#assertThat(Object, String) assertThat()} is used when assertions
	 *         are disabled, the verifier does not need to retain a reference to the actual value)
	 */
	Optional<T> getActualIfPresent();

	/**
	 * Returns the actual value.
	 * <p>
	 * Beware: the return value may be wrapped to prevent modification.
	 *
	 * @return the actual value
	 * @throws NoSuchElementException if the verifier does not have access to the actual value (e.g.
	 *                                if {@link Requirements#assertThat(Object, String) assertThat()}
	 *                                is used when assertions are disabled, the verifier does not need
	 *                                to retain a reference to the actual value)
	 */
	T getActual();
}
