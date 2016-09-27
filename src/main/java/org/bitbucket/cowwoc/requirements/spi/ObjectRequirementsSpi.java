/*
 * Copyright 2015 Gili Tzabari.
 * Licensed under the Apache License, Version 2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.bitbucket.cowwoc.requirements.spi;

import com.google.common.annotations.Beta;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

/**
 * Verifies requirements of an {@link Object} parameter.
 * <p>
 * @param <S> the type of the non-SPI interface extending this interface
 * @param <T> the type of the parameter
 * @author Gili Tzabari
 */
public interface ObjectRequirementsSpi<S extends ObjectRequirementsSpi<S, T>, T>
{
	/**
	 * Overrides the type of exception that will get thrown if a requirement fails.
	 * <p>
	 * The exception class must define the following constructors:
	 * <p>
	 * {@code <init>(String message)}<br>
	 * {@code <init>(String message, Throwable cause)}
	 *
	 * @param exception the type of exception to throw, null to disable the override
	 * @return a verifier with the specified exception override
	 */
	S withException(Class<? extends RuntimeException> exception);

	/**
	 * Adds contextual information to append to the exception message.
	 *
	 * @param key   a key
	 * @param value a value
	 * @return a verifier with the specified context
	 * @throws NullPointerException if {@code key} is null
	 */
	@Beta
	S addContext(String key, Object value) throws NullPointerException;

	/**
	 * Sets the contextual information to append to the exception message.
	 *
	 * @param context the contextual information
	 * @return a verifier with the specified context
	 * @throws NullPointerException if {@code context} is null
	 */
	@Beta
	S withContext(List<Entry<String, Object>> context) throws NullPointerException;

	/**
	 * Ensures that the parameter is equal to a value.
	 * <p>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if parameter is not equal to value
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is equal to a variable.
	 * <p>
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is not equal to the variable; if
	 *                                  {@code name} is empty
	 * @see <a href="https://bitbucket.org/cowwoc/requirements/wiki/Textual_diff">An explanation of the output format</a>
	 */
	S isEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a value.
	 * <p>
	 * @param value the value to compare to
	 * @return this
	 * @throws IllegalArgumentException if {@code parameter} is equal to value
	 */
	S isNotEqualTo(T value) throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not equal to a variable.
	 * <p>
	 * @param value the value to compare to
	 * @param name  the name of the variable
	 * @return this
	 * @throws NullPointerException     if {@code name} is null
	 * @throws IllegalArgumentException if {@code parameter} is equal to the variable; if {@code name}
	 *                                  is empty
	 */
	S isNotEqualTo(T value, String name) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that a collection contains the parameter.
	 *
	 * @param collection a collection
	 * @return this
	 * @throws NullPointerException     if {@code collection} is null
	 * @throws IllegalArgumentException if {@code collection} does not contain {@code parameter}
	 */
	@Beta
	S isIn(Collection<T> collection)
		throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is an instance of a class.
	 * <p>
	 * @param type the class to compare to
	 * @return this
	 * @throws NullPointerException     if {@code type} are null
	 * @throws IllegalArgumentException if {@code parameter} is not an instance of {@code type}
	 */
	S isInstanceOf(Class<?> type) throws NullPointerException, IllegalArgumentException;

	/**
	 * Ensures that the parameter is null.
	 * <p>
	 * @return this
	 * @throws IllegalArgumentException if parameter is not null
	 */
	S isNull() throws IllegalArgumentException;

	/**
	 * Ensures that the parameter is not null.
	 * <p>
	 * @return this
	 * @throws NullPointerException if parameter is null
	 */
	S isNotNull() throws NullPointerException;
}
