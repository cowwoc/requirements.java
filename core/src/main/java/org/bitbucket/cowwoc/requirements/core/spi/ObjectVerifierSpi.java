/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.core.spi;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.bitbucket.cowwoc.requirements.core.StringVerifier;

/**
 * Verifies an {@link Object} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectVerifierSpi<S extends ObjectVerifierSpi<S, T>, T> extends Verifier
{
	@Override
	S withException(Class<? extends RuntimeException> exception);

	@Override
	S addContext(String key, Object value);

	@Override
	S withContext(List<Entry<String, Object>> context);

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
}
